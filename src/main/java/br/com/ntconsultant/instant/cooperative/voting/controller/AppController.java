package br.com.ntconsultant.instant.cooperative.voting.controller;

import br.com.ntconsultant.instant.cooperative.voting.dto.PermittedCpfVote;
import br.com.ntconsultant.instant.cooperative.voting.enums.TypePermittedCpfVote;
import br.com.ntconsultant.instant.cooperative.voting.exceptions.ResourceStatusNotFoundException;
import br.com.ntconsultant.instant.cooperative.voting.facade.ConsultCpFacade;
import br.com.ntconsultant.instant.cooperative.voting.service.PautaReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author Daniel Santos
 * Home redirection to swagger api documentation
 */
@Controller
@Slf4j
@RequestMapping("/v1")
@RequiredArgsConstructor
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
