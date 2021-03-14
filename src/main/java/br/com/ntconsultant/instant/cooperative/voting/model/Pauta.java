package br.com.ntconsultant.instant.cooperative.voting.model;

import br.com.ntconsultant.instant.cooperative.voting.enums.TypeStatusSession;
import br.com.ntconsultant.instant.cooperative.voting.enums.VoteType;
import br.com.ntconsultant.instant.cooperative.voting.util.FormatterUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Daniel Santos
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
@Slf4j
@Document(collection = "pauta")
public class Pauta implements IGenerateReleaseDate {

    @Id
    private String id;

    private String title;

    private Session session;

    private LocalDateTime dtCreated;

    @Transient
    private TypeStatusSession typeStatusSession;

    public void generateDtCreated() {
        this.dtCreated = IGenerateReleaseDate.generateDtRelease();
    }

    @JsonIgnore
    public Pauta generateDtCreatedThis() {
        generateDtCreated();
        return this;
    }

    @JsonIgnore
    public Pauta beginSession(Instant fim) {
        session = Session.start(fim);
        log.info("Session: to open session {} with the date {}.", FormatterUtil.formatterLocalDateTimeFrom(session.getEnd()), FormatterUtil.formatterLocalDateTimeBy(session.getDtCreated()));
        generateTypeStatusSession();
        return this;
    }

    @JsonIgnore
    public Pauta vote(Vote vote) {
        session.addVote(vote);
        return this;
    }

    public TypeStatusSession getTypeStatusSession() {
        generateTypeStatusSession();
        return this.typeStatusSession;
    }

    public void generateTypeStatusSession() {
        if (Objects.isNull(this.session)) {
            this.typeStatusSession = TypeStatusSession.UNDEFINED;
            return;
        }

        this.typeStatusSession = (isFinished()) ? TypeStatusSession.FINISHED : TypeStatusSession.OPENING;
    }

    @JsonIgnore
    public boolean isFinished() {
        return Optional.ofNullable(session)
                .map(Session::isFinished)
                .orElse(false);
    }

    @JsonIgnore
    public boolean isOpeningType() {
        return (Objects.nonNull(this.typeStatusSession) && this.typeStatusSession.isOpening());
    }

    @JsonIgnore
    public boolean isFinishedType() {
        return (Objects.nonNull(this.typeStatusSession) && this.typeStatusSession.isFinished());
    }

    @JsonIgnore
    public Map<VoteType, Long> generateVoteTotalizers() {
        return (isInvalidVotes())
                ? Collections.emptyMap()
                : this.session.getVotes().stream().collect(Collectors.groupingBy(Vote::getVoteType, Collectors.counting()));
    }

    @JsonIgnore
    public Pauta generateVoteTotalizersTo() {
        generateVoteTotalizers();
        return this;
    }

    @JsonIgnore
    public boolean isInvalidVotes() {
        return (Objects.isNull(this.session) || Objects.isNull(this.session.getVotes()) || this.session.getVotes().isEmpty());
    }

    @JsonIgnore
    public String toStringTypeStatusSession() {
        return (Objects.isNull(this.typeStatusSession)) ? "" : this.typeStatusSession.getCode();
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
