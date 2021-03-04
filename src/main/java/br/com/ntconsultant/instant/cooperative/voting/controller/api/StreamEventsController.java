package br.com.ntconsultant.instant.cooperative.voting.controller.api;

import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.service.IPautaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;

/**
 * @author Daniel Santos
 */
@RestController
@RequestMapping("/v1/pautas/events")
@Slf4j
@RequiredArgsConstructor
public class StreamEventsController {

    private final IPautaService pautaService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tuple2<Long, Pauta>> getPautaByEvents() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(5));

        Flux<Pauta> events = pautaService.findAll();
        log.info("Stream de eventos em execucao...");
        return Flux.zip(interval, events);
    }
}
