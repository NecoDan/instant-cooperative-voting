package br.com.ntconsultant.instant.cooperative.voting;

import br.com.ntconsultant.instant.cooperative.voting.properties.ApiProperties;
import br.com.ntconsultant.instant.cooperative.voting.properties.JobProperties;
import br.com.ntconsultant.instant.cooperative.voting.properties.KafkaVotingProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableScheduling
@EnableConfigurationProperties({ApiProperties.class, JobProperties.class, KafkaVotingProperties.class})
public class InstantCooperativeVotingApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstantCooperativeVotingApplication.class, args);
    }
}
