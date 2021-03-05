package br.com.ntconsultant.instant.cooperative.voting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Daniel Santos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PautaModel {
    private String id;
    private String titulo;
    private SessionModel sessionModel;
    private LocalDateTime dt;
}
