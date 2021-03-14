package br.com.ntconsultant.instant.cooperative.voting.kafka.resource;

import br.com.ntconsultant.instant.cooperative.voting.enums.VoteType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class VotingResultMessage {

    private String title;

    private String status;

    @JsonProperty("voting_result")
    Map<VoteType, Long> voteModels = new HashMap<>();
}
