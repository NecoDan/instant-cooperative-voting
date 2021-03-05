package br.com.ntconsultant.instant.cooperative.voting.controller.api.voters;

import br.com.ntconsultant.instant.cooperative.voting.dto.PautaModel;
import br.com.ntconsultant.instant.cooperative.voting.dto.PermittedCpfVote;
import br.com.ntconsultant.instant.cooperative.voting.enums.TypePermittedCpfVote;
import br.com.ntconsultant.instant.cooperative.voting.exceptions.PautaUnProcessableEntityException;
import br.com.ntconsultant.instant.cooperative.voting.facade.ConsultCpFacade;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.service.IGenerateVotingService;
import br.com.ntconsultant.instant.cooperative.voting.service.IPautaReportService;
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

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Daniel Santos
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class VoterHandler {

    private final ConsultCpFacade consultCpFacade;

    // @ApiOperation(value = "Call to create pauta.", response = PautaModel.class)
    public Mono<ServerResponse> getCpfVoterPermitedVote(ServerRequest request) {
        String cpf = request.pathVariable("cpf");
        log.info("Searching for cpf is valid and enabled for voting by {}.", cpf);

        final Optional<PermittedCpfVote> optPermittedCpfVote
                = consultCpFacade.getTypePermittedCpfVote(cpf);

        if (optPermittedCpfVote.isPresent()){
            PermittedCpfVote permittedCpfVote = optPermittedCpfVote.get();

            return Mono.just(TypePermittedCpfVote.of(permittedCpfVote.getStatus()))
                    .flatMap(t -> ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON).bodyValue(t))
                    .doOnSuccess(t -> log.info("Returning {} is enabled for voting", cpf))
                    .switchIfEmpty(ServerResponse.notFound().build());
        }

        return ServerResponse.notFound().build();
    }
}
