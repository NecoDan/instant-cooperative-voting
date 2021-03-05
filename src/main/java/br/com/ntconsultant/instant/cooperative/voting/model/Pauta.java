package br.com.ntconsultant.instant.cooperative.voting.model;

import br.com.ntconsultant.instant.cooperative.voting.enums.TypeStatusSession;
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

    private TypeStatusSession typeStatusSession;

    private LocalDateTime dtCreated;

    public void generateDtCreated() {
        this.dtCreated = IGenerateReleaseDate.generateDtRelease();
    }

    public Pauta generateDtCreatedThis() {
        generateDtCreated();
        return this;
    }

    public Pauta beginSession(Instant fim) {
        session = Session.start(fim);
        log.info("Session: to open session {} with the date {}.", FormatterUtil.formatterLocalDateTimeFrom(session.getEnd()), FormatterUtil.formatterLocalDateTimeBy(session.getDtCreated()));
        generateTypeStatusSession();
        return this;
    }

    public Pauta vote(Vote vote) {
        session.addVote(vote);
        return this;
    }

    public void generateTypeStatusSession() {
        if (Objects.isNull(this.session)) {
            this.typeStatusSession = TypeStatusSession.UNDEFINED;
            return;
        }

        this.typeStatusSession = (isFinished()) ? TypeStatusSession.FINISHED : TypeStatusSession.OPENING;
    }

    public boolean isFinished() {
        return Optional.ofNullable(session)
                .map(Session::isFinished)
                .orElse(false);
    }

    public boolean isOpeningType() {
        return (Objects.nonNull(this.typeStatusSession) && this.typeStatusSession.isOpening());
    }

    public boolean isFinishedType() {
        return (Objects.nonNull(this.typeStatusSession) && this.typeStatusSession.isFinished());
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
