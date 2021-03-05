package br.com.ntconsultant.instant.cooperative.voting.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniel Santos
 */
@Data
@Builder
public class Session implements InitializeEndSession, IGenerateReleaseDate {

    private String id;

    private Instant end;

    private LocalDateTime dtCreated;

    private List<Vote> votes;

    public static Session start(Instant end) {
        return Session.builder()
                .id(UUID.randomUUID().toString())
                .end(InitializeEndSession.start(end))
                .build()
                .generateDtCreatedThis();
    }

    public boolean isFinished() {
        return Instant.now().isAfter(end);
    }

    public void generateDtCreated() {
        this.dtCreated = IGenerateReleaseDate.generateDtRelease();
    }

    public Session generateDtCreatedThis() {
        generateDtCreated();
        return this;
    }

    public boolean isRegisteredVoteForVoterBy(String idVoter) {
        return (isExistsRegisteredVotes() && (votes.stream().anyMatch(voto -> voto.getIdVoter().equals(idVoter))));
    }

    public boolean isExistsRegisteredVotes() {
        return (Objects.nonNull(this.votes));
    }

    public void addVote(Vote vote) {
        if (Objects.isNull(this.votes))
            this.votes = new ArrayList<>();

        vote.generateId();
        vote.generateDtCreated();
        this.votes.add(vote);
    }
}
