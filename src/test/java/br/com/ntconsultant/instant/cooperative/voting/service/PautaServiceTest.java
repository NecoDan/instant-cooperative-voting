package br.com.ntconsultant.instant.cooperative.voting.service;

import br.com.ntconsultant.instant.cooperative.voting.exceptions.PautaNotFoundException;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.model.Session;
import br.com.ntconsultant.instant.cooperative.voting.repository.PautaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Daniel Santos
 */
@ExtendWith(MockitoExtension.class)
class PautaServiceTest {

    @InjectMocks
    private PautaService pautaService;
    @Mock
    private PautaRepository pautaRepository;

    @Captor
    private ArgumentCaptor<Pauta> pautaArgumentCaptor;

    @Test
    void mustSaveANewPauta() {
        Pauta pautaSave = getPautaWithoutSession("Votacao");
        pautaService.save(pautaSave);
        verify(pautaRepository, times(1)).save(pautaArgumentCaptor.capture());
        assertEquals(pautaSave, pautaArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("should search pauta findById returning error")
    void shouldSearchPautaReturningError() {
        when(pautaRepository.findById(anyString()))
                .thenReturn(Mono.empty());

        assertThrows(PautaNotFoundException.class,
                () -> pautaService.findById(UUID.randomUUID().toString()).block()
        );
    }

    public Pauta getPautaWithoutSession(String title) {
        return Pauta.builder()
                .id(UUID.randomUUID().toString())
                .title(title)
                .session(null)
                .build()
                .generateDtCreatedThis();
    }

    public Pauta getPautaWithFinishedSession() {
        Pauta pauta = getPautaWithoutSession("Nome");
        pauta.setSession(Session.start(Instant.now().minusSeconds(10)));
        return pauta;
    }

    public Pauta getPautaWithyOpeningSession() {
        Pauta pauta = getPautaWithoutSession("Nome");
        pauta.setSession(Session.start(null));
        return pauta;
    }
}
