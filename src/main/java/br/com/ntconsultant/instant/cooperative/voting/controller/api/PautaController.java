package br.com.ntconsultant.instant.cooperative.voting.controller.api;

import br.com.ntconsultant.instant.cooperative.voting.service.IPautaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Daniel Santos
 */
@RestController
@RequestMapping("/v1/pautas/")
@Slf4j
@RequiredArgsConstructor
public class PautaController {
    private final IPautaService pautaService;
}
