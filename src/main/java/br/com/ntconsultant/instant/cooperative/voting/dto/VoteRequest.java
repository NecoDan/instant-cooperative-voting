package br.com.ntconsultant.instant.cooperative.voting.dto;

import br.com.ntconsultant.instant.cooperative.voting.enums.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Daniel Santos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoteRequest {
    private String idVoter;
    private VoteType voteType;
}
