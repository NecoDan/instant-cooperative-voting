package br.com.ntconsultant.instant.cooperative.voting.model;

import br.com.ntconsultant.instant.cooperative.voting.exceptions.ExistingSessionException;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Daniel Santos
 */
@Document
@Data
@Builder
public class Pauta {
    @Id
    private String id;
    private String title;
    @DBRef
    private Session session;
    private LocalDateTime dt;

    public void generateDtRelease() {
        this.dt = LocalDateTime.now();
    }

    public Pauta generateDtReleaseThis() {
        generateDtRelease();
        return this;
    }

    public Pauta beginSession(Instant fim) {
        if (session != null) {
            throw new ExistingSessionException(id);
        }

        session = Session.iniciar(fim);
        return this;
    }

    public boolean isFinished() {
        return Optional.ofNullable(session)
                .map(Session::isFinalizada)
                .orElse(false);
    }
}
