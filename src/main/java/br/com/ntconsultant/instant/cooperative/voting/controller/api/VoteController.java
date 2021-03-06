package br.com.ntconsultant.instant.cooperative.voting.controller.api;

import br.com.ntconsultant.instant.cooperative.voting.enums.TypePermittedCpfVote;
import br.com.ntconsultant.instant.cooperative.voting.facade.ConsultCpFacade;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Daniel Santos
 */
@RestController
@RequestMapping("/v1/votes")
@Slf4j
@RequiredArgsConstructor
public class VoteController {

    private final ConsultCpFacade consultCpFacade;

    @GetMapping("/consulted-cpf-permitted-vote/{cpf}")
    @Operation(summary = "Returns a specific status is enabled for voting by cpf", tags = "pautas")
    public Mono<TypePermittedCpfVote> getCpfVoterPermitedVote(@PathVariable("cpf") String cpf) {
        log.info("In Searching for cpf is valid and enabled for voting by {}.", cpf);

        return consultCpFacade.getTypePermittedCpfVote(cpf)
                .map(permittedCpfVote -> Mono.just(TypePermittedCpfVote.of(permittedCpfVote.getStatus()))
                        .doOnSuccess(t -> log.info("Returning {} is enabled for voting", cpf)))
                .orElse(Mono.just(TypePermittedCpfVote.UNABLE_TO_VOTE));
    }
}
