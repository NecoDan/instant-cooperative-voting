package br.com.ntconsultant.instant.cooperative.voting.controller.api.events;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * @author Daniel Santos
 */
@Configuration
public class VoterRouter {

    private static final String URI = "/v1/events/votes";

    @Bean
    public RouterFunction<ServerResponse> routeVotersFunction(VoterHandler handler) {
        return RouterFunctions
                .route(GET(URI + "/consulted-cpf-permitted-vote/{cpf}")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::getCpfVoterPermitedVote);
    }
}
