package br.com.ntconsultant.instant.cooperative.voting.service;

import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.repository.PautaRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;
import java.util.function.Predicate;

@Log4j2
@DataMongoTest
@Import(PautaService.class)
class PautaServiceIntegracaoTest {

    private final IPautaService pautaService;
    private final PautaRepository pautaRepository;

    public PautaServiceIntegracaoTest(@Autowired IPautaService pautaService,
                                      @Autowired PautaRepository pautaRepository) {
        this.pautaService = pautaService;
        this.pautaRepository = pautaRepository;
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {
        Flux<Pauta> saved = this.pautaRepository.saveAll(Flux.just(
                getPauta("Pauta 1"),
                getPauta("Pauta 2"),
                getPauta("Pauta 3"),
                getPauta("Pauta 4")
        ));

        Flux<Pauta> composite = pautaService.findAll().thenMany(saved);
        Predicate<Pauta> match = pauta -> saved.any(saveItem -> saveItem.equals(pauta)).block();

        StepVerifier
                .create(composite)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .verifyComplete();
    }

    @Test
    void findById() {
        String id = UUID.randomUUID().toString();
        String title = "Pauta qualquer";

        Pauta pauta = getPauta(title);
        pauta.setId(id);

        Mono<Pauta> pautaCreated = this.pautaService
                .save(pauta)
                .flatMap(saved -> this.pautaService.findById(saved.getId()));

        StepVerifier
                .create(pautaCreated)
                .expectNextMatches(p -> StringUtils.hasText(p.getId())
                        && title.equalsIgnoreCase(p.getTitle())
                        && id.equalsIgnoreCase(p.getId())
                )
                .verifyComplete();
    }

    @Test
    void save() {
        Pauta pauta = getPauta("Outra Pauta Qualquer");
        Mono<Pauta> pautaSaved = this.pautaService.save(pauta);

        StepVerifier
                .create(pautaSaved)
                .expectNextMatches(saved -> StringUtils.hasText(saved.getId()))
                .verifyComplete();
    }

    private Pauta getPauta(String title) {
        return Pauta.builder().title(title).build().generateDtCreatedThis();
    }
}
