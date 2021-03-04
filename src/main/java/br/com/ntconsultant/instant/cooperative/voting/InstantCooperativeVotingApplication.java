package br.com.ntconsultant.instant.cooperative.voting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class InstantCooperativeVotingApplication {
    public static void main(String[] args) {
        SpringApplication.run(InstantCooperativeVotingApplication.class, args);
    }
}
