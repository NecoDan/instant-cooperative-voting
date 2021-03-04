package br.com.ntconsultant.instant.cooperative.voting.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Daniel Santos
 * Home redirection to swagger api documentation
 */
@Controller
@RequestMapping("/v1")
//@Api("API RESTFUL management of polls on the agendas.")
public class AppController {

    @Value("${app.message}")
    private String appMessage;

    //    @ApiOperation(value = "API initialization identification")
    @GetMapping("/")
    public String getAppMessage() {
        return appMessage;
    }

//    @Value(SWAGGER_UI_PATH)
//    private String swaggerUiPath;
//
//    @GetMapping(DEFAULT_PATH_SEPARATOR)
//    public Mono<Void> index(ServerHttpRequest request, ServerHttpResponse response) {
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(swaggerUiPath);
//        response.setStatusCode(HttpStatus.TEMPORARY_REDIRECT);
//        response.getHeaders().setLocation(URI.create(uriBuilder.build().encode().toString()));
//        return response.setComplete();
//    }
}
