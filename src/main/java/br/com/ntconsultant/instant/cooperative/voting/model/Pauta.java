package br.com.ntconsultant.instant.cooperative.voting.model;

import br.com.ntconsultant.instant.cooperative.voting.exceptions.ExistingSessionException;
import br.com.ntconsultant.instant.cooperative.voting.util.FormatterUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;


/**
 * @author Daniel Santos
 */
@Data
@Builder
@Slf4j
@Document(collection = "pauta")
public class Pauta implements IGenerateReleaseDate {
    @Id
    private String id;
    private String title;
    private Session session;
    private LocalDateTime dt;

    public void generateDtRelease() {
        this.dt = IGenerateReleaseDate.generateDtRelease();
    }

    public Pauta generateDtReleaseThis() {
        generateDtRelease();
        return this;
    }

    public Pauta beginSession(Instant fim) {
        if (Objects.nonNull(this.session)) {
            throw new ExistingSessionException(id);
        }

        session = Session.iniciar(fim);
        log.info("Session: to open session {} with the date {}.", FormatterUtil.formatterLocalDateTimeFrom(session.getEnd()), FormatterUtil.formatterLocalDateTimeBy(session.getDt()));
        return this;
    }

    public boolean isFinished() {
        return Optional.ofNullable(session)
                .map(Session::isFinished)
                .orElse(false);
    }


    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        String jsonString = "";
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            jsonString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

}
