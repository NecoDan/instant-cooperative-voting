package br.com.ntconsultant.instant.cooperative.voting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Daniel Santos
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermittedCpfVote {
    private String status;
    private String cpf;
}
