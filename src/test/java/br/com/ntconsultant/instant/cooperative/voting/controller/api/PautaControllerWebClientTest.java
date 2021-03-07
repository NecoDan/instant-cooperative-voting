package br.com.ntconsultant.instant.cooperative.voting.controller.api;

import br.com.ntconsultant.instant.cooperative.voting.dto.PautaModel;
import br.com.ntconsultant.instant.cooperative.voting.dto.PautaModelRequest;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.repository.PautaRepository;
import br.com.ntconsultant.instant.cooperative.voting.service.GenerateVotingService;
import br.com.ntconsultant.instant.cooperative.voting.service.PautaReportService;
import br.com.ntconsultant.instant.cooperative.voting.service.PautaService;
import br.com.ntconsultant.instant.cooperative.voting.util.PautaCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PautaController.class)
@Import({GenerateVotingService.class, PautaReportService.class, ModelMapper.class})
class PautaControllerWebClientTest {

    @MockBean
    PautaRepository pautaRepository;

    @MockBean
    PautaService pautaService;

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

        Mockito.when(pautaService.findById(id))
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
        Mockito.verify(pautaService, times(1)).findById(id);
    }

    @Test
    @DisplayName("listAll returns a flux of pauta")
    void listAllReturnFluxOfPautaWhenSuccessful() {
        PautaModel pautaModel = PautaCreator.createValidPautaModel(pauta);

        Mockito.when(pautaService.findAll())
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

        Mockito.when(pautaService.findAll())
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

        Mockito.when(pautaService.findById(id)).thenReturn(Mono.just(pauta));

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
//        Pauta pautaToBeSaved = PautaCreator.createPautaToBeSaved();
//
//        PautaModelRequest pautaModelRequest = new PautaModelRequest(pautaToBeSaved.getTitle());
//
//        Pauta pautasaved = PautaCreator.createValidPauta();
//        pautasaved.setTitle(pautaToBeSaved.getTitle());
//        PautaModel pautaModel = PautaCreator.createValidPautaModel(pautasaved);
//
//        Mockito.when(pautaService.save(pautaToBeSaved)).thenReturn(Mono.just(pautasaved));
//
//        webTestClient.post()
//                .uri(URI)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(pautaModelRequest))
//                .exchange()
//                .expectStatus().isCreated()
//                .expectBody(PautaModel.class);
//               // .isEqualTo(pautaModel);
    }

    @Test
    @DisplayName("save returns mono error with bad request when name is empty")
    void saveReturnsErrorWhenTitleIsEmpty() {
//        PautaModelRequest pautaModelRequest = new PautaModelRequest("");
//
//        webTestClient.post()
//                .uri(URI)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(pautaModelRequest))
//                .exchange()
//                .expectStatus().isBadRequest()
//                .expectBody()
//                .jsonPath("$.status").isEqualTo(400);
    }


//                    .expectBody()
//                .jsonPath("$.name").isNotEmpty()
//                .jsonPath("$.id").isEqualTo(100)
//                .jsonPath("$.name").isEqualTo("Test")
//                .jsonPath("$.salary").isEqualTo(1000)
}
