package br.com.ntconsultant.instant.cooperative.voting.controller.api;

import br.com.ntconsultant.instant.cooperative.voting.dto.OpeningSessionRequest;
import br.com.ntconsultant.instant.cooperative.voting.dto.PautaModel;
import br.com.ntconsultant.instant.cooperative.voting.dto.VoteRequest;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.model.Vote;
import br.com.ntconsultant.instant.cooperative.voting.service.IGenerateVotingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author Daniel Santos
 */
@RestController
@RequestMapping("/v1/pautas/")
@Slf4j
@RequiredArgsConstructor
public class PautaController {

    private final IGenerateVotingService generateVotingService;
    private final ModelMapper modelMapper;

    @PostMapping("/opening-session/{idPauta}")
    public Mono<ResponseEntity> openingSession(@PathVariable("idPauta") String idPauta,
                                               @RequestBody OpeningSessionRequest openingSessionRequest) {
        log.info("Pauta {} attempt to open session {}.", idPauta, openingSessionRequest);
        return generateVotingService.openingSessionVoting(idPauta, openingSessionRequest.getEnd())
                .map(it -> (ResponseEntity) ResponseEntity.ok().build())
                .doOnSuccess(it -> log.info("Session in successfully.."));
    }

    @PostMapping("/vote/{idPauta}")
    public Mono<ResponseEntity> votar(@PathVariable("idPauta") String idPauta,
                                      @RequestBody @Validated VoteRequest voteRequest) {
        log.info("Pauta {} making a new vote {}.", idPauta, voteRequest);
        return generateVotingService.vote(idPauta, toVoteFrom(voteRequest))
                .map(it -> (ResponseEntity) ResponseEntity.ok().build())
                .doOnSuccess(it -> log.info("Vote in successfuly..."));
    }

    private Vote toVoteFrom(VoteRequest voteRequest) {
        return this.modelMapper.map(voteRequest, Vote.class);
    }
}
