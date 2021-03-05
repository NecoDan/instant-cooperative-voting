package br.com.ntconsultant.instant.cooperative.voting.model;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Daniel Santos
 */
public interface InitializeEndSession {

    static final Integer VALUE_DEFAULT_MINUTES = 600;

    static Instant iniciar(Instant end) {
        return Optional.ofNullable(end).orElseGet(() -> Instant.now().plusSeconds(VALUE_DEFAULT_MINUTES));
    }
}
