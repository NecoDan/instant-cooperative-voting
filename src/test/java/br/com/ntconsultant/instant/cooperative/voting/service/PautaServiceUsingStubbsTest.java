package br.com.ntconsultant.instant.cooperative.voting.service;

import br.com.ntconsultant.instant.cooperative.voting.exceptions.PautaNotFoundException;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.repository.PautaRepository;
import br.com.ntconsultant.instant.cooperative.voting.util.PautaCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Daniel Santos
 * @since 07/03/2021
 */
@ExtendWith(SpringExtension.class)
class PautaServiceUsingStubbsTest {

    @Mock
    private PautaRepository pautaRepository;
    @InjectMocks
    private PautaService pautaService;

    private final Pauta pauta = PautaCreator.createValidPauta();

    /**
     * Definição pra mantermos a certeza que o código não está sendo bloqueado por nenhuma thread ou bloqueando
     * nenhuma thread
     */
    @BeforeAll
    public static void BlockHoundSetup() {
        BlockHound.install();
    }

    @BeforeEach
    public void setUp() {
        BDDMockito.when(pautaRepository.findAll())
                .thenReturn(Flux.just(pauta));

        BDDMockito.when(pautaRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(pauta));

        BDDMockito.when(pautaRepository.save(PautaCreator.createPautaToBeSaved()))
                .thenReturn(Mono.just(pauta));

        BDDMockito.when(pautaRepository.delete(ArgumentMatchers.any(Pauta.class)))
                .thenReturn(Mono.empty());

        BDDMockito.when(pautaRepository.save(PautaCreator.createValidPauta()))
                .thenReturn(Mono.empty());
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

    @Test
    @DisplayName("save creates an pauta when successful")
    void saveCreatesPautaWhenSuccessful() {
        Pauta pautaToBeSaved = PautaCreator.createPautaToBeSaved();

        StepVerifier.create(pautaService.save(pautaToBeSaved))
                .expectSubscription()
                .expectNext(pauta)
                .verifyComplete();
    }

    @Test
    @DisplayName("update save updated pauta returns empty mono when successful")
    void updateSaveUpdatedPautaWhenSuccessful() {
        StepVerifier.create(pautaService.update(PautaCreator.createValidPauta()))
                .expectSubscription()
                .expectNoEvent(Duration.ZERO);
    }

    @Test
    @DisplayName("findById returns a Mono with pauta when it exists")
    void findByIdReturnMonoPautaWhenSuccessful() {
        StepVerifier.create(pautaService.findById(UUID.randomUUID().toString()))
                .expectSubscription()
                .expectNext(pauta)
                .verifyComplete();
    }

    @Test
    @DisplayName("findAll returns a flux of pauta")
    void findAllReturnFluxOfPautaWhenSuccessful() {
        StepVerifier.create(pautaService.findAll())
                .expectSubscription()
                .expectNext(pauta)
                .verifyComplete();
    }

    @Test
    @DisplayName("findById returns Mono error when pauta does not exist")
    void findByIdReturnMonoErrorWhenEmptyMonoIsReturned() {
        BDDMockito.when(pautaRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(pautaService.findById(UUID.randomUUID().toString()))
                .expectSubscription()
                .expectError(PautaNotFoundException.class)
                .verify();
    }
}
