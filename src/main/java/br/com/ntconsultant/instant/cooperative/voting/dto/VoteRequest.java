package br.com.ntconsultant.instant.cooperative.voting.dto;

import br.com.ntconsultant.instant.cooperative.voting.enums.VoteType;
import lombok.*;

/**
 * @author Daniel Santos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VoteRequest {
    private String idVoter;
    private VoteType voteType;
}
