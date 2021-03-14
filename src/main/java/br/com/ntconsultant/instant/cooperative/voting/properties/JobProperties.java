package br.com.ntconsultant.instant.cooperative.voting.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "job")
public class JobProperties {
    private String cronJob;
}
