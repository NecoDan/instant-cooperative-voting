package br.com.ntconsultant.instant.cooperative.voting.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
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
@JsonRootName("Session")
public class SessionModel {

    private String id;

    private Instant end;

    private LocalDateTime dt;
}
