package br.com.ntconsultant.instant.cooperative.voting.controller.api.voters;

import br.com.ntconsultant.instant.cooperative.voting.controller.api.pautas.PautaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * @author Daniel Santos
 */
@Configuration
public class VoterRouter {

    private static final String URI = "/v1/voters";

    @Bean
    public RouterFunction<ServerResponse> routePautas(VoterHandler handler) {
        return RouterFunctions
                .route(GET(URI + "/consulted-cpf-permitted-vote/{cpf}")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::getCpfVoterPermitedVote);
    }
}
