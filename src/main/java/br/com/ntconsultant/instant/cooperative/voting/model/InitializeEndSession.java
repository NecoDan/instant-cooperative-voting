package br.com.ntconsultant.instant.cooperative.voting.model;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Daniel Santos
 */
public interface InitializeEndSession {

    static Instant iniciar(Instant end) {
        if (Objects.isNull(end))
            throw new IllegalArgumentException("End parameter invalid for generation and start of session.");
        return Optional.ofNullable(end).orElseGet(() -> Instant.now().plusSeconds(60));
    }
}
