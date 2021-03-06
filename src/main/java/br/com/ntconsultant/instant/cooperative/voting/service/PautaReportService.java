package br.com.ntconsultant.instant.cooperative.voting.service;

import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PautaReportService implements IPautaReportService {

    private final IPautaService pautaService;

    @Override
    public Flux<Pauta> getAll() {
        return this.pautaService.findAll();
    }

    @Override
    public Mono<Pauta> getOneBy(String id) {
        return this.pautaService.findById(id);
    }

}
