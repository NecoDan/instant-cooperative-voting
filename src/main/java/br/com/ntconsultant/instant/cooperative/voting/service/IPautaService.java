package br.com.ntconsultant.instant.cooperative.voting.service;

import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Daniel Santos
 */
public interface IPautaService {

    Flux<Pauta> findAll();

    Mono<Pauta> findById(String id);

    Mono<Pauta> save(Pauta pautaMono);

    Mono<Pauta> update(Pauta pauta);
}
