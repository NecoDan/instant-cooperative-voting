package br.com.ntconsultant.instant.cooperative.voting.facade;

import br.com.ntconsultant.instant.cooperative.voting.dto.PermittedCpfVote;
import br.com.ntconsultant.instant.cooperative.voting.exceptions.VoterNotFoundException;
import br.com.ntconsultant.instant.cooperative.voting.properties.ApiProperties;
import br.com.ntconsultant.instant.cooperative.voting.util.RestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Daniel Santos
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ConsultCpFacade {

    private final ApiProperties apiProperties;
    private final RestUtils restUtils;
    private static final String CPF_VOTER_ID = "cpf_voter";

    public Optional<PermittedCpfVote> getTypePermittedCpfVote(String cpf){
        try {
            String url = apiProperties.getUrlCpfConsultedBy(cpf);

            PermittedCpfVote permittedCpfVote = restUtils
                    .responseObject(PermittedCpfVote.class, url, HttpMethod.GET, getParamCpfId(cpf));
            permittedCpfVote.setCpf(cpf);

            return Optional.of(permittedCpfVote);
        }catch (Exception e){
            log.error("Error get permitted cpf to vote for cpf id | {}", cpf);
            throw new VoterNotFoundException("Error get permitted cpf to vote for cpf" + cpf);
        }
    }

    private HttpEntity<?> getParamCpfId(String cpf) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CPF_VOTER_ID, cpf);
        return new HttpEntity<>(headers);
    }
}
