package br.com.ntconsultant.instant.cooperative.voting.service;

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

import java.util.UUID;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Daniel Santos
 * @since 07/03/2021
 */
@ExtendWith(SpringExtension.class)
class PautaReportServiceTest {

    @Mock
    private PautaRepository pautaRepository;
    @Mock
    private PautaService pautaService;
    @InjectMocks
    private PautaReportService pautaReportService;

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
        BDDMockito.when(pautaService.findAll())
                .thenReturn(Flux.just(pauta));

        BDDMockito.when(pautaService.findById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(pauta));
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
    @DisplayName("getAll returns a flux of pauta")
    void getAllReturnFluxOfPautaWhenSuccessful() {
        StepVerifier.create(pautaReportService.getAll())
                .expectSubscription()
                .expectNext(pauta)
                .verifyComplete();
    }

    @Test
    @DisplayName("getOneBy returns a Mono with pauta when it exists")
    void getOneByIdReturnMonoPautaWhenSuccessful() {
        StepVerifier.create(pautaReportService.getOneBy(UUID.randomUUID().toString()))
                .expectSubscription()
                .expectNext(pauta)
                .verifyComplete();
    }
}
