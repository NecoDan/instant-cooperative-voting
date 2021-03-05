package br.com.ntconsultant.instant.cooperative.voting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<VoteModel> voteModels = new ArrayList<>();

    @JsonProperty("dt_created")
    private LocalDateTime dtCreated;
}
