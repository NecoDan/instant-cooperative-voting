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

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Santos
 */
@ExtendWith(SpringExtension.class)
class GenerateVotingServiceTest {

    @Mock
    private PautaService pautaService;
    @InjectMocks
    private GenerateVotingService generateVotingService;

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
        BDDMockito.when(pautaService.save(PautaCreator.createPautaToBeSaved()))
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
    @DisplayName("creates an pauta when successful")
    void createsPautaWhenSuccessful() {
//        Pauta pautaToBeSaved = PautaCreator.createPautaToBeSaved();
//
//        StepVerifier.create(generateVotingService.create(pautaToBeSaved))
//                .expectSubscription()
//                .expectNext(pauta)
//                .verifyComplete();
    }

    @Test
    void openingSessionVoting() {
    }

    @Test
    void vote() {
    }
}
