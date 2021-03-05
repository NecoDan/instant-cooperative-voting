package br.com.ntconsultant.instant.cooperative.voting.dto;

import br.com.ntconsultant.instant.cooperative.voting.enums.VoteType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel Santos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VotingResult {

    private String idPauta;

    private String titlePauta;

    @JsonProperty("voting_result")
    Map<VoteType, Long> voteModels = new HashMap<>();
}
