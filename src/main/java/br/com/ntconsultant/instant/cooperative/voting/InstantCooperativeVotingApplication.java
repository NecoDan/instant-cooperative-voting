package br.com.ntconsultant.instant.cooperative.voting;

import br.com.ntconsultant.instant.cooperative.voting.properties.ApiProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.blockhound.BlockHound;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableConfigurationProperties({ApiProperties.class})
public class InstantCooperativeVotingApplication {

    static {
        BlockHound.install(
                builder -> builder.allowBlockingCallsInside("java.util.UUID", "randomUUID")
                        .allowBlockingCallsInside("java.io.FilterInputStream", "read")
                        .allowBlockingCallsInside("java.io.InputStream", "readNBytes")
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(InstantCooperativeVotingApplication.class, args);
    }


}
