package br.com.ntconsultant.instant.cooperative.voting.config;

import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Daniel Santos
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DummyData implements CommandLineRunner {

    private final PautaRepository pautaRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Inicializado lançamentos massa de dados...");
        // lancarRegistrosMassaDados();
    }

    private void lancarRegistrosMassaDados() {
        this.pautaRepository.deleteAll()
                .thenMany(
                        Flux.just("Deve estabelecer criterios aceite na historia?",
                                "Deve sempre fazer commit diario",
                                "Cafe com ou sem leite?",
                                "Quem nasceu primeiro ovo ou a galinha?",
                                "Cafe com ou sem açucar")
                                .map(this::obterPautaPor)
                                .flatMap(pautaRepository::save))
                .subscribe(System.out::println);
    }

    private Pauta obterPautaPor(String titulo) {
        return Pauta.builder()
                .id(UUID.randomUUID().toString())
                .title(titulo)
                .dt(LocalDateTime.now())
                .session(null)
                .build();
    }
}
