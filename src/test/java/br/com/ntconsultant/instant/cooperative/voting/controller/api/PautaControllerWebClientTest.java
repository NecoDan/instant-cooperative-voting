package br.com.ntconsultant.instant.cooperative.voting.controller.api;

import br.com.ntconsultant.instant.cooperative.voting.dto.OpeningSessionRequest;
import br.com.ntconsultant.instant.cooperative.voting.dto.PautaModel;
import br.com.ntconsultant.instant.cooperative.voting.dto.PautaModelRequest;
import br.com.ntconsultant.instant.cooperative.voting.dto.VoteRequest;
import br.com.ntconsultant.instant.cooperative.voting.enums.VoteType;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.repository.PautaRepository;
import br.com.ntconsultant.instant.cooperative.voting.service.GenerateVotingService;
import br.com.ntconsultant.instant.cooperative.voting.service.PautaReportService;
import br.com.ntconsultant.instant.cooperative.voting.service.PautaService;
import br.com.ntconsultant.instant.cooperative.voting.util.PautaCreator;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@WebFluxTest
@ContextConfiguration(classes = TestConfiguration.class)
@AutoConfigureWebTestClient(timeout = "10000")
class PautaControllerWebClientTest {

    private PautaRepository pautaRepository;
    private PautaService pautaService;
    private PautaReportService pautaReportService;
    private GenerateVotingService generateVotingService;
    private ModelMapper modelMapper;
    @Autowired
    private WebTestClient webTestClient;

    private static final String URI = "/v1/pautas/";
    private final Pauta pauta = PautaCreator.createValidPauta();

    /**
     * Definição pra mantermos a certeza que o código não está sendo bloqueado por nenhuma thread ou bloqueando
     * nenhuma thread
     */
    @BeforeAll
    public static void blockHoundSetup() {
        BlockHound.install();
    }

    @BeforeEach
    void initClient() {
        this.modelMapper = PautaCreator.createModelMapperForTests();

        this.pautaRepository = Mockito.mock(PautaRepository.class);
        this.pautaService = new PautaService(pautaRepository);
        this.pautaReportService = new PautaReportService(pautaService);
        this.generateVotingService = new GenerateVotingService(pautaService);

        webTestClient = WebTestClient
                .bindToController(new PautaController(generateVotingService, pautaReportService, modelMapper))
                .build();
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
    void findByIdPautaReturnMonoPautaWhenSuccessful() {
        String id = pauta.getId();
        PautaModel pautaModel = PautaCreator.createValidPautaModel(pauta);

        Mockito.when(pautaRepository.findById(id))
                .thenReturn(Mono.just(pauta));

        webTestClient.get()
                .uri(URI + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(PautaModel.class)
                .value(response -> {
                    assertNotNull(response);
                    Assert.isTrue(response.get(0).getId().equals(pautaModel.getId()), pauta.getId());
                    Assert.isTrue(response.get(0).getTitle().equals(pautaModel.getTitle()), pauta.getTitle());
                    Assert.notNull(response.get(0).getStatus());
                });

        Mockito.verify(pautaRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("listAll returns a flux of pauta")
    void listAllReturnFluxOfPautaWhenSuccessful() {
        PautaModel pautaModel = PautaCreator.createValidPautaModel(pauta);

        Mockito.when(pautaRepository.findAll())
                .thenReturn(Flux.just(pauta,
                        PautaCreator.createValidPauta(),
                        PautaCreator.createValidPauta(),
                        PautaCreator.createValidPauta())
                );

        webTestClient.get()
                .uri(URI)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(pautaModel.getId())
                .jsonPath("$.[0].title").isEqualTo(pautaModel.getTitle());
    }

    @Test
    @DisplayName("listAll returns a flux of pauta")
    void listAllFlavor2ReturnFluxOfPautaWhenSuccessful() {
        PautaModel pautaModel = PautaCreator.createValidPautaModel(pauta);

        Mockito.when(pautaRepository.findAll())
                .thenReturn(Flux.just(pauta,
                        PautaCreator.createValidPauta(),
                        PautaCreator.createValidPauta(),
                        PautaCreator.createValidPauta())
                );

        webTestClient.get()
                .uri(URI)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PautaModel.class)
                .hasSize(4)
                .contains(pautaModel);
    }

    @Test
    @DisplayName("findById returns a Mono with pauta when it exists")
    void findByIdReturnMonoPautaWhenSuccessful() {
        String id = pauta.getId();
        PautaModel pautaModel = PautaCreator.createValidPautaModel(pauta);

        Mockito.when(pautaRepository.findById(id)).thenReturn(Mono.just(pauta));

        webTestClient.get()
                .uri(URI + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PautaModel.class)
                .isEqualTo(pautaModel);
    }

    @Test
    @DisplayName("save creates an pauta when successful")
    void saveCreatesPautaWhenSuccessful() {
        Pauta pautaToBeSaved = PautaCreator.createPautaToBeSaved();
        PautaModelRequest pautaModelRequest = new PautaModelRequest(pautaToBeSaved.getTitle());

        Pauta pautasaved = PautaCreator.createValidPauta();
        pautasaved.setTitle(pautaToBeSaved.getTitle());
        PautaModel pautaModel = PautaCreator.createValidPautaModel(pautasaved);

        Mockito.when(pautaRepository.save(any())).thenReturn(Mono.just(pautasaved));

        PautaModel pautaModelResponse = webTestClient.post()
                .uri(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(pautaModelRequest))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated()
                .returnResult(PautaModel.class)
                .getResponseBody()
                .blockFirst();

        assertNotNull(pautaModelResponse);
        assertThat(pautaModelResponse.getId()).isEqualTo(pautasaved.getId());
        assertThat(pautaModelResponse.getTitle()).isEqualTo(pautasaved.getTitle());
        assertThat(pautaModelResponse.getStatus()).isEqualTo(pautasaved.getTypeStatusSession().getCode());
    }

    @Test
    @DisplayName("save returns mono error with bad request when name is empty")
    void saveReturnsErrorWhenTitleIsEmpty() {
        PautaModelRequest pautaModelRequest = new PautaModelRequest("");

        webTestClient.post()
                .uri(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(pautaModelRequest))
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Test
    @DisplayName("save update an pauta by initializing your session when successful")
    void saveUpdatePautaInitializingSessionWhenSuccessful() {
        OpeningSessionRequest openingSessionRequest = new OpeningSessionRequest();

        Pauta pautasaved = PautaCreator.createValidPauta();
        pautasaved.setTitle(PautaCreator.createPautaToBeSaved().getTitle());

        Pauta pautaParam = pautasaved;
        pautaParam.setSession(null);
        String id = pautasaved.getId();

        Mockito.when(pautaRepository.findById(anyString())).thenReturn(Mono.just(pautaParam));
        Mockito.when(pautaRepository.save(any())).thenReturn(Mono.just(pautasaved));

        WebTestClient.ResponseSpec responseSpec = webTestClient.post()
                .uri(URI + "/opening-session/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(openingSessionRequest))
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        assertNotNull(responseSpec);
        assertThat(responseSpec.expectStatus().isOk());
    }

    @Test
    @DisplayName("save update an pauta when voting when successful")
    void saveUpdatePautaVoteWhenSuccessful() {
        Integer minutes = 60*10;

        VoteRequest voteRequest = VoteRequest.builder()
                .idVoter("41051691060")
                .voteType(VoteType.SIM)
                .build();

        Pauta pautasaved = PautaCreator.createValidPauta();
        pautasaved.setTitle(PautaCreator.createPautaToBeSaved().getTitle());

        Pauta pautaParam = pautasaved;
        pautaParam.getSession().setEnd(Instant.now().plusSeconds(minutes));
        String id = pautasaved.getId();

        Mockito.when(pautaRepository.findById(anyString())).thenReturn(Mono.just(pautaParam));
        Mockito.when(pautaRepository.save(any())).thenReturn(Mono.just(pautasaved));

        WebTestClient.ResponseSpec responseSpec = webTestClient.post()
                .uri(URI + "/vote/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(voteRequest))
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        assertNotNull(responseSpec);
        assertThat(responseSpec.expectStatus().isOk());
    }
}
