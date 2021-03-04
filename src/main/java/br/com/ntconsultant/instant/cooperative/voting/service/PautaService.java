package br.com.ntconsultant.instant.cooperative.voting.service;

import br.com.ntconsultant.instant.cooperative.voting.exceptions.PautaException;
import br.com.ntconsultant.instant.cooperative.voting.exceptions.PautaNotFoundException;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author Daniel Santos
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PautaService implements IPautaService {

    private final PautaRepository pautaRepository;

    @Override
    public Flux<Pauta> findAll() {
        return this.pautaRepository.findAll();
    }

    @Override
    public Mono<Pauta> findById(String id) {
        return this.pautaRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new PautaNotFoundException(id))));
    }

    @Override
    public Mono<Pauta> save(Pauta pauta) throws PautaException{
        if (Objects.isNull(pauta))
            throw new PautaException("Algum errro");

        pauta.gerarDtLancamento();
        return this.pautaRepository.save(pauta);
    }
}
