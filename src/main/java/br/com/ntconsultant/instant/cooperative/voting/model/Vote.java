package br.com.ntconsultant.instant.cooperative.voting.model;

import br.com.ntconsultant.instant.cooperative.voting.enums.TipoVoto;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author Daniel Santos
 */
@Document
@Data
@Builder
public class Vote {
    @Id
    private String id;
    private String idEleitor;
    private TipoVoto tipoVoto;
    private LocalDateTime dt;
}
