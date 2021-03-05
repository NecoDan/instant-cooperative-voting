package br.com.ntconsultant.instant.cooperative.voting.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Daniel Santos
 */
@Document
@Data
@Builder
public class Session implements InitializeEndSession {
    @Id
    private String id;
    private Instant end;
    private LocalDateTime dt;
    private List<Vote> votes;

    public static Session iniciar(Instant end) {
        return Session.builder().end(InitializeEndSession.iniciar(end)).build();
    }

    boolean isFinalizada() {
        return Instant.now().isAfter(end);
    }
}
