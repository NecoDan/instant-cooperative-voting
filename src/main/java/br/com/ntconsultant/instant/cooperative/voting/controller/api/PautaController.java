package br.com.ntconsultant.instant.cooperative.voting.controller.api;

import br.com.ntconsultant.instant.cooperative.voting.dto.OpeningSessionRequest;
import br.com.ntconsultant.instant.cooperative.voting.service.IGenerateVotingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author Daniel Santos
 */
@RestController
@RequestMapping("/v1/pautas/")
@Slf4j
@RequiredArgsConstructor
public class PautaController {

    private final IGenerateVotingService generateVotingService;

    @PostMapping("/opening-session/{idPauta}")
    public Mono<ResponseEntity> openingSession(@PathVariable("idPauta") String idPauta,
                                               @RequestBody OpeningSessionRequest openingSessionRequest) {
        log.info("Pauta {} attempt to open session {}.", idPauta, openingSessionRequest);
        return generateVotingService.openingSessionVoting(idPauta, openingSessionRequest.getEnd())
                .map(it -> (ResponseEntity) ResponseEntity.ok().build())
                .doOnSuccess(it -> log.info("Session in successfully.."));
    }
}
