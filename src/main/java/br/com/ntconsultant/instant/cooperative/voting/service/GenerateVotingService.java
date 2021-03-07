package br.com.ntconsultant.instant.cooperative.voting.service;

import br.com.ntconsultant.instant.cooperative.voting.exceptions.*;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.model.Session;
import br.com.ntconsultant.instant.cooperative.voting.model.Vote;
import br.com.ntconsultant.instant.cooperative.voting.util.FormatterUtil;
import io.netty.util.internal.StringUtil;
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
        if (Objects.isNull(pauta)) {
            String msg = "GenerateVotingService - Error: Trying to create Pauta. Pauta is invalid and/or nonexistent (null).";
            log.error(msg);
            throw new PautaUnProcessableEntityException(msg);
        }

        pauta.generateDtCreated();
        pauta.generateTypeStatusSession();
        return this.pautaService.save(pauta);
    }

    @Override
    public Mono<Void> openingSessionVoting(String idPauta, Instant endSession) {
        if (StringUtil.isNullOrEmpty(idPauta)) {
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

    private Pauta processBeginSession(Pauta pauta, Instant endSession) {
        if (Objects.nonNull(pauta.getSession())) {
            throw new ExistingSessionException(pauta.getId());
        }
        return pauta.beginSession(endSession);
    }

    @Override
    public Mono<Void> vote(String idPauta, Vote vote) {
        if (StringUtil.isNullOrEmpty(idPauta)) {
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
    public Pauta processVote(Pauta pauta, Vote vote) {
        Session session = pauta.getSession();
        validateParamsVoteProcessing(pauta, session, vote);
        return pauta.vote(vote);
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
