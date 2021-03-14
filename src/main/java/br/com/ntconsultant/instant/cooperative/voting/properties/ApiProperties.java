package br.com.ntconsultant.instant.cooperative.voting.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

    private String cpfConsultedUrl;

    public String getUrlCpfConsultedBy(String cpf){
        return getCpfConsultedUrl().replace("{cpf}", cpf);
    }
}
