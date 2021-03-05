package br.com.ntconsultant.instant.cooperative.voting.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Daniel Santos
 */
@Data
@Builder
public class Session implements InitializeEndSession, IGenerateReleaseDate {
    private String id;
    private Instant end;
    private LocalDateTime dt;
    private List<Vote> votes;

    public static Session iniciar(Instant end) {
        return Session.builder()
                .id(UUID.randomUUID().toString())
                .end(InitializeEndSession.iniciar(end))
                .build()
                .generateDtReleaseThis();
    }

    boolean isFinished() {
        return Instant.now().isAfter(end);
    }

    public void generateDtRelease() {
        this.dt = IGenerateReleaseDate.generateDtRelease();
    }

    public Session generateDtReleaseThis() {
        generateDtRelease();
        return this;
    }
}
