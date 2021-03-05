package br.com.ntconsultant.instant.cooperative.voting.service;

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
    public Mono<Void> openingSessionVoting(String idPauta, Instant endSession) {
        log.info("GenerateVotingService: Pauta {} tries to open session with the end {}.",
                idPauta, Objects.isNull(endSession) ? "" : FormatterUtil.formatterLocalDateTimeFrom(endSession));

        return this.pautaService.findById(idPauta)
                .map(pauta -> pauta.beginSession(endSession))
                .flatMap(pautaService::save)
                .then();
    }
}
