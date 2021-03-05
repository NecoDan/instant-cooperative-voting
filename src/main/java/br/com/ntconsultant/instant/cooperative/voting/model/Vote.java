package br.com.ntconsultant.instant.cooperative.voting.model;

import br.com.ntconsultant.instant.cooperative.voting.enums.VoteType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Daniel Santos
 */
@Data
@Builder
public class Vote {

    private String id;

    private String idVoter;

    private VoteType voteType;

    private LocalDateTime dt;
}
