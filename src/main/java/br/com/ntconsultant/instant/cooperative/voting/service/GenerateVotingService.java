package br.com.ntconsultant.instant.cooperative.voting.service;

import br.com.ntconsultant.instant.cooperative.voting.exceptions.*;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.model.Session;
import br.com.ntconsultant.instant.cooperative.voting.model.Vote;
import br.com.ntconsultant.instant.cooperative.voting.util.FormatterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Objects;

/**
 * @author Daniel Santos
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GenerateVotingService implements IGenerateVotingService {

    private final IPautaService pautaService;

    @Override
    public Mono<Pauta> create(Pauta pauta) throws PautaException {
        validateParams(pauta);
        pauta.generateDtCreated();
        pauta.generateTypeStatusSession();
        return this.pautaService.save(pauta);
    }

    @Override
    public Mono<Void> openingSessionVoting(String idPauta, Instant endSession) {
        if (Objects.isNull(idPauta) || idPauta.isEmpty()) {
            String msg = "GenerateVotingService - Error: Trying to open session by Pauta Id is invalid.";
            log.error(msg);
            throw new PautaUnProcessableEntityException(msg);
        }

        log.info("GenerateVotingService: Pauta {} tries to open session with the end {}.",
                idPauta, Objects.isNull(endSession) ? "" : FormatterUtil.formatterLocalDateTimeFrom(endSession));

        return this.pautaService.findById(idPauta)
                .map(pauta -> processBeginSession(pauta, endSession))
                .flatMap(pautaService::save)
                .then();
    }

    @Override
    public Mono<Void> vote(String idPauta, Vote vote) {
        if (Objects.isNull(idPauta) || idPauta.isEmpty()) {
            String msg = "GenerateVotingService - Error: Trying to vote by Pauta Id is invalid.";
            log.error(msg);
            throw new PautaUnProcessableEntityException(msg);
        }

        log.info("GenerateVotingService: Pauta {} tries to voting with {}.", idPauta, vote);
        return this.pautaService.findById(idPauta)
                .map(pauta -> processVote(pauta, vote))
                .flatMap(pautaService::save)
                .then();
    }

    @Override
    public Pauta processBeginSession(Pauta pauta, Instant endSession) {
        if (Objects.nonNull(pauta.getSession())) {
            throw new ExistingSessionException(pauta.getId());
        }
        return pauta.beginSession(endSession);
    }

    @Override
    public Pauta processVote(Pauta pauta, Vote vote) {
        Session session = pauta.getSession();
        validateParamsVoteProcessing(pauta, session, vote);
        return pauta.vote(vote);
    }

    @Override
    public void validateParams(Pauta pauta) {
        if (Objects.isNull(pauta)) {
            String msg = "GenerateVotingService - Error: Trying to create Pauta. The Pauta title is invalid and / or not informed..";
            log.error(msg);
            throw new PautaUnProcessableEntityException(msg);
        }

        if (Objects.isNull(pauta.getTitle()) || pauta.getTitle().isEmpty()) {
            String msg = "GenerateVotingService - Error: Trying to create Pauta. Pauta is invalid and/or nonexistent (null).";
            log.error(msg);
            throw new PautaUnProcessableEntityException(msg);
        }
    }

    @Override
    public void validateParamsVoteProcessing(Pauta pauta, Session session, Vote vote) {
        if (Objects.isNull(session)) {
            throw new SessionNotFounException(pauta.getId());
        }

        if (session.isFinished()) {
            throw new SessionFinishedException(FormatterUtil.formatterLocalDateTimeFrom(session.getEnd()));
        }

        if (session.isRegisteredVoteForVoterBy(vote.getIdVoter())) {
            throw new VoterHasAlreadyVotedException(vote.getIdVoter());
        }
    }
}
