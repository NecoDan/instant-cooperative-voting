package br.com.ntconsultant.instant.cooperative.voting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author Daniel Santos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SessionModel {
    private String id;
    private Instant fim;
    private LocalDateTime dt;
}
