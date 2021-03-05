package br.com.ntconsultant.instant.cooperative.voting.dto;

import br.com.ntconsultant.instant.cooperative.voting.enums.VoteType;
import br.com.ntconsultant.instant.cooperative.voting.model.Vote;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Daniel Santos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PautaModel {

    private String id;

    private String title;

    private String status;

    @JsonProperty("voting_result")
    Map<VoteType, Long> voteModels = new HashMap<>();

    @JsonProperty("dt_created")
    private LocalDateTime dtCreated;

    public void generateVoteTotalizers(List<Vote> votesList) {
        if (Objects.isNull(votesList) || votesList.isEmpty())
            return;

        voteModels = votesList.stream()
                .collect(Collectors.groupingBy(Vote::getVoteType, Collectors.counting()));
    }
}
