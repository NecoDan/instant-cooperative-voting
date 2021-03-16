package br.com.ntconsultant.instant.cooperative.voting.controller;

import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.service.IPautaReportService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/v1/events-stream")
@Slf4j
@RequiredArgsConstructor
public class StreamEventsController {

    private final IPautaReportService pautaReportService;
    private static final Integer DURATION_SECONDS = 5;

    @GetMapping(value = "/pautas/produces", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "Searching all existing pauta(s)", tags = "events-stream")
    public Flux<Tuple2<Long, Pauta>> getPautaByEvents() {
        log.info("Running event stream pautas...");
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(DURATION_SECONDS));
        Flux<Pauta> events = pautaReportService.getAll();
        return Flux.zip(interval, events);
    }
}
