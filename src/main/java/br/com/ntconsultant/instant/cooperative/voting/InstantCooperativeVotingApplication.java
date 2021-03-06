package br.com.ntconsultant.instant.cooperative.voting;

import br.com.ntconsultant.instant.cooperative.voting.properties.ApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableConfigurationProperties({ApiProperties.class})
public class InstantCooperativeVotingApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstantCooperativeVotingApplication.class, args);
    }
}
