package br.com.ntconsultant.instant.cooperative.voting.model;

import br.com.ntconsultant.instant.cooperative.voting.enums.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Daniel Santos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vote implements IGenerateReleaseDate{

    private String id;

    private String idVoter;

    private VoteType voteType;

    private LocalDateTime dtCreated;

    public void generateId(){
        this.id = UUID.randomUUID().toString();
    }

    public void generateDtCreated() {
        this.dtCreated = IGenerateReleaseDate.generateDtRelease();
    }

    public Vote generateDtCreatedThis() {
        generateDtCreated();
        return this;
    }
}
