package br.com.ntconsultant.instant.cooperative.voting.controller.api;

import br.com.ntconsultant.instant.cooperative.voting.dto.OpeningSessionRequest;
import br.com.ntconsultant.instant.cooperative.voting.dto.PautaModel;
import br.com.ntconsultant.instant.cooperative.voting.dto.PautaModelRequest;
import br.com.ntconsultant.instant.cooperative.voting.dto.VoteRequest;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.model.Vote;
import br.com.ntconsultant.instant.cooperative.voting.service.IGenerateVotingService;
import br.com.ntconsultant.instant.cooperative.voting.service.IPautaReportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Objects;

/**
 * @author Daniel Santos
 */
@RestController
@RequestMapping("/v1/pautas")
@Slf4j
@RequiredArgsConstructor
public class PautaController {

    private final IGenerateVotingService generateVotingService;
    private final IPautaReportService pautaReportService;
    private final ModelMapper modelMapper;

    @PostMapping
    @Operation(summary = "Call to create pauta.", tags = "pautas")
    public Mono<ResponseEntity<PautaModel>> criarPauta(@RequestBody @Valid PautaModelRequest pautaModelRequest) {
        log.info("Call to create pauta: {}.", pautaModelRequest);

        return generateVotingService.create(toPautaFrom(pautaModelRequest))
                .map(p -> toPautaModelCreate(p))
                .map(pautaResponse -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(pautaResponse))
                .doOnSuccess(pautaResponse -> log.info("Pauta criada com sucesso!"));
    }

    @PostMapping("/opening-session/{idPauta}")
    @Operation(summary = "Starts a session on the specified pauta.", tags = "pautas")
    public Mono<ResponseEntity> openingSession(@PathVariable("idPauta") String idPauta,
                                               @RequestBody OpeningSessionRequest openingSessionRequest) {

        log.info("Pauta {} attempt to open session {}.", idPauta, openingSessionRequest);

        return generateVotingService.openingSessionVoting(idPauta, openingSessionRequest.getEnd())
                .map(it -> (ResponseEntity) ResponseEntity.ok().build())
                .doOnSuccess(it -> log.info("Session in successfully.."));
    }

    @PostMapping("/vote/{idPauta}")
    @Operation(summary = "Performs the vote on the specified pauta.", tags = "pautas")
    public Mono<ResponseEntity> votar(@PathVariable("idPauta") String idPauta,
                                      @RequestBody @Validated VoteRequest voteRequest) {

        log.info("Pauta {} making a new vote {}.", idPauta, voteRequest);

        return generateVotingService.vote(idPauta, toVoteFrom(voteRequest))
                .map(it -> (ResponseEntity) ResponseEntity.ok().build())
                .doOnSuccess(it -> log.info("Vote in successfuly..."));
    }

    @GetMapping
    @Operation(summary = "Returns a list of all pautas.", tags = "pautas")
    public Flux<PautaModel> findAll() {

        log.info("Searching all existing Pauta(s)...");
        return pautaReportService.getAll().map(this::toPautaModel).doOnComplete(() -> log.info("Returning list of Pauta(s)."));
    }

    @GetMapping("/{idPauta}")
    @Operation(summary = "Returns a specific pauta by id", tags = "pautas")
    public Mono<PautaModel> findById(@PathVariable("idPauta") String idPauta) {
        log.info("Searching for an existing Pauta by Id ...");

        return pautaReportService.getOneBy(idPauta)
                .map(this::toPautaModel)
                .doOnSuccess(p -> log.info("Returning a specific Pauta by {Id} =" + idPauta));
    }

    public PautaModel toPautaModel(Pauta pauta) {
        PautaModel pautaModel = toPautaModelCreating(pauta);

        pautaModel.setStatus((Objects.isNull(pauta.getTypeStatusSession())) ? "" : pauta.getTypeStatusSession().getCode());
        pautaModel.generateVoteTotalizers(Objects.isNull(pauta.getSession()) ? Collections.emptyList() : pauta.getSession().getVotes());

        return pautaModel;
    }

    public PautaModel toPautaModelCreate(Pauta pauta) {
        log.info("Call to create pauta: {}.", pauta);
        return toPautaModelCreating(pauta);
    }

    public PautaModel toPautaModelCreating(Pauta pauta) {
        return this.modelMapper.map(pauta, PautaModel.class);
    }

    public Pauta toPautaFrom(PautaModelRequest pautaModelRequest) {
        return this.modelMapper.map(pautaModelRequest, Pauta.class);
    }

    private Vote toVoteFrom(VoteRequest voteRequest) {
        return this.modelMapper.map(voteRequest, Vote.class);
    }
}
