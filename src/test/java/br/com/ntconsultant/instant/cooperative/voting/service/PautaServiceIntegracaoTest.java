package br.com.ntconsultant.instant.cooperative.voting.service;

import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.repository.PautaRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.UUID;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
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

    /**
     * Definição pra mantermos a certeza que o código não está sendo bloqueado por nenhuma thread ou bloqueando
     * nenhuma thread
     */
    @BeforeAll
    public static void BlockHoundSetup() {
        BlockHound.install();
    }

    /**
     * Método de teste que identifica se o BlockHound está sendo carregado em todas as execuções dos testes desta classe
     */
    @Test
    void blockHoundWorks() {
        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0); //NOSONAR
                return "";
            });

            Schedulers.parallel().schedule(task);
            task.get(10, TimeUnit.SECONDS);
            Assertions.fail("should fail");
        } catch (Exception e) {
            Assertions.assertTrue(e.getCause() instanceof BlockingOperationError);
        }
    }

//    @Test
//    @Order(3)
//    void findAll() {
//        Flux<Pauta> saved = this.pautaRepository.saveAll(Flux.just(
//                getPauta(UUID.randomUUID().toString()),
//                getPauta(UUID.randomUUID().toString()),
//                getPauta(UUID.randomUUID().toString()),
//                getPauta(UUID.randomUUID().toString())
//        ));
//
//        Flux<Pauta> composite = pautaService.findAll().thenMany(saved);
//        Predicate<Pauta> match = pauta -> saved.any(saveItem -> saveItem.equals(pauta)).block();
//
//        StepVerifier.create(composite)
//                .expectNextMatches(match)
//                .expectNextMatches(match)
//                .expectNextMatches(match)
//                .expectNextMatches(match)
//                .verifyComplete();
//    }

    @Test
    @Order(2)
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
    @Order(1)
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
