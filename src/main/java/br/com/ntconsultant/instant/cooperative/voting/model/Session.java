package br.com.ntconsultant.instant.cooperative.voting.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Daniel Santos
 */
@Document
@Data
@Builder
public class Session {
    @Id
    private String id;
    private Instant fim;
    private LocalDateTime dt;
    private List<Vote> votes;
}
