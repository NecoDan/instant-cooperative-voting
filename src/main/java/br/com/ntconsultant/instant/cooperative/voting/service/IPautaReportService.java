package br.com.ntconsultant.instant.cooperative.voting.service;

import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPautaReportService {
    Flux<Pauta> getAll();

    Mono<Pauta> getOneBy(String id);
}
