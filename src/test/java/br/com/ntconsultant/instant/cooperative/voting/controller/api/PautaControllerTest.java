package br.com.ntconsultant.instant.cooperative.voting.controller.api;

import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.repository.PautaRepository;
import br.com.ntconsultant.instant.cooperative.voting.service.GenerateVotingService;
import br.com.ntconsultant.instant.cooperative.voting.service.PautaReportService;
import br.com.ntconsultant.instant.cooperative.voting.service.PautaService;
import br.com.ntconsultant.instant.cooperative.voting.util.PautaCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Daniel Santos
 */
@ExtendWith(SpringExtension.class)
class PautaControllerTest {

    @Mock
    private PautaRepository pautaRepository;
    @Mock
    private PautaService pautaService;
    @Mock
    private PautaReportService pautaReportService;
    @Mock
    private GenerateVotingService generateVotingService;
    @Mock
    ModelMapper modelMapper;
    @InjectMocks
    private PautaController pautaController;

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
        BDDMockito.when(pautaReportService.getAll())
                .thenReturn(Flux.just(pauta, PautaCreator.createValidPauta(), PautaCreator.createValidPauta(), PautaCreator.createValidPauta()));

        BDDMockito.when(pautaReportService.getOneBy(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(pauta));

        BDDMockito.when(generateVotingService.create(PautaCreator.createPautaToBeSaved()))
                .thenReturn(Mono.just(pauta));

        BDDMockito.when(pautaRepository.findAll())
                .thenReturn(Flux.just(pauta, PautaCreator.createValidPauta(), PautaCreator.createValidPauta(), PautaCreator.createValidPauta()));

        BDDMockito.when(pautaRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(pauta));

        BDDMockito.when(pautaRepository.save(PautaCreator.createPautaToBeSaved()))
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
    @DisplayName("findAll returns a flux of pauta in controller")
    void findAllReturnFluxOfPautaWhenSuccessful() {
//        PautaModel pautaModel = PautaCreator.createValidPautaModel(PautaCreator.createValidPauta());
//
//        StepVerifier.create(pautaController.findAll())
//                .expectSubscription()
//                .expectNext(pautaModel)
//                .verifyComplete();
    }

}
