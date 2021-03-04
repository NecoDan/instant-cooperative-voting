package br.com.ntconsultant.instant.cooperative.voting.controller.api;

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
public class PautaRouter {

    private static final String URI = "/v1/pautas";

    @Bean
    public RouterFunction<ServerResponse> route(PautaHandler handler) {
        return RouterFunctions
                .route(GET(URI)
                        .and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
                .andRoute(GET(URI + "/{id}")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::findById)
                .andRoute(POST(URI)
                        .and(accept(MediaType.APPLICATION_JSON)), handler::save);
    }
}
