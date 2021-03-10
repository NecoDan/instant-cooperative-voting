package br.com.ntconsultant.instant.cooperative.voting.service;

import br.com.ntconsultant.instant.cooperative.voting.enums.VoteType;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.model.Vote;
import br.com.ntconsultant.instant.cooperative.voting.repository.PautaRepository;
import br.com.ntconsultant.instant.cooperative.voting.util.PautaCreator;
import br.com.ntconsultant.instant.cooperative.voting.util.RandomUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Daniel Santos
 * @since 07/03/2021
 */
@ExtendWith(SpringExtension.class)
class GenerateVotingServiceTest {

    @Mock
    private PautaRepository pautaRepository;
    @InjectMocks
    private PautaService pautaService;

    private GenerateVotingService generateVotingService;

    private final Pauta pauta = PautaCreator.createValidPauta();
    private final Pauta pautaToBeSaved = PautaCreator.createPautaToBeSaved();

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
        String id = pauta.getId();
        this.generateVotingService = new GenerateVotingService(pautaService);

        BDDMockito.when(pautaRepository.save(pautaToBeSaved))
                .thenReturn(Mono.just(pauta));
        BDDMockito.when(pautaService.save(pautaToBeSaved))
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
    @DisplayName("save creates an pauta when successful")
    void createPautaWhenSuccessful() {
        StepVerifier.create(generateVotingService.create(pautaToBeSaved))
                .expectSubscription()
                .expectNext(pauta)
                .verifyComplete();
    }

    @Test
    @DisplayName("save update open session on the pauta successfully")
    void saveUpdateOpenSessionPautaWhenSuccessful() {
        Pauta pautaParam = pauta;
        pautaParam.setSession(null);
        String id = pautaParam.getId();

        Integer minutes = 60 * RandomUtil.generateRandomValueUntil(60);
        Instant end = Instant.now().plusSeconds(minutes);

        BDDMockito.when(pautaRepository.findById(id))
                .thenReturn(Mono.just(pautaParam));
        BDDMockito.when(pautaService.findById(id))
                .thenReturn(Mono.just(pautaParam));
        BDDMockito.when(pautaRepository.save(pautaParam))
                .thenReturn(Mono.just(pauta));
        BDDMockito.when(pautaService.save(pautaParam))
                .thenReturn(Mono.just(pauta));

        StepVerifier.create(generateVotingService.openingSessionVoting(id, end))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("save update voting on the pauta successfully")
    void saveUpdateVotingPautaWhenSuccessful() {
        Pauta pautaParam = pauta;
        String id = pautaParam.getId();

        Integer minutes = 60 * RandomUtil.generateRandomValueUntil(60);
        Instant end = Instant.now().plusSeconds(minutes);
        pautaParam.getSession().setEnd(end);

        Vote vote = new Vote();
        vote.setIdVoter("91630221090");
        vote.setVoteType(VoteType.SIM);

        BDDMockito.when(pautaRepository.findById(id))
                .thenReturn(Mono.just(pautaParam));
        BDDMockito.when(pautaService.findById(id))
                .thenReturn(Mono.just(pautaParam));
        BDDMockito.when(pautaRepository.save(pautaParam))
                .thenReturn(Mono.just(pauta));
        BDDMockito.when(pautaService.save(pautaParam))
                .thenReturn(Mono.just(pauta));

        StepVerifier.create(generateVotingService.vote(id, vote))
                .expectNextCount(0)
                .verifyComplete();
    }
}
