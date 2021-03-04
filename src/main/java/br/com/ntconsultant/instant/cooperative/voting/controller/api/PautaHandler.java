package br.com.ntconsultant.instant.cooperative.voting.controller.api;

import br.com.ntconsultant.instant.cooperative.voting.dto.PautaModel;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.service.IPautaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author Daniel Santos
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class PautaHandler {

    private final IPautaService pautaService;
    private final ModelMapper modelMapper;

    // @ApiOperation(value = "Returns a list of all pautas.", response = PautaModel[].class)
//    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Returns a list of all pautas")})
    public Mono<ServerResponse> findAll(ServerRequest request) {
        log.info("Searching all existing Pauta(s)...");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pautaService.findAll()
                                .map(this::toPautaModel)
                                .doOnComplete(() -> log.info("Returning list of Pauta(s).")),
                        PautaModel.class);
    }

    //  @ApiOperation(value = "Returns a specific pauta by id", response = PautaModel.class)
    public Mono<ServerResponse> findById(ServerRequest request) {
        log.info("Searching for an existing Pauta by Id ...");
        String id = request.pathVariable("id");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pautaService.findById(id)
                                .map(this::toPautaModel)
                                .doOnSuccess(p -> log.info("Returning a specific Pauta by {Id} =" + id)),
                        PautaModel.class);
    }

    // @ApiOperation(value = "Call to create pauta.", response = PautaModel.class)
    public Mono<ServerResponse> save(ServerRequest request) {
        final Mono<Pauta> pauta = request.bodyToMono(Pauta.class);
        final Mono<Pauta> pautaSave = pauta.flatMap(pautaService::save);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pautaSave.map(this::toPautaModelCreate)
                        .map(pautaResponse -> ResponseEntity.status(HttpStatus.CREATED).body(pautaResponse))
                        .doOnSuccess(pautaResponse -> log.info("Pauta criada com sucesso!")), PautaModel.class);
    }

    private PautaModel toPautaModel(Pauta pauta) {
        return this.modelMapper.map(pauta, PautaModel.class);
    }

    private PautaModel toPautaModelCreate(Pauta pauta) {
        log.info("Call to create pauta: {}.", pauta);
        return this.modelMapper.map(pauta, PautaModel.class);
    }
}