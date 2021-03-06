package br.com.ntconsultant.instant.cooperative.voting.model;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Daniel Santos
 */
public interface InitializeEndSession {

    Integer VALUE_DEFAULT_SECONDS = 60;

    static Instant start(Instant end) {
        return Optional.ofNullable(end).orElseGet(() -> Instant.now().plusSeconds(VALUE_DEFAULT_SECONDS));
    }
}
