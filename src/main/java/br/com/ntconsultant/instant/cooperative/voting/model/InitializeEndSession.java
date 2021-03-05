package br.com.ntconsultant.instant.cooperative.voting.model;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Daniel Santos
 */
public interface InitializeEndSession {

    static Instant iniciar(Instant end) {
        return Optional.ofNullable(end).orElseGet(() -> Instant.now().plusSeconds(60));
    }
}
