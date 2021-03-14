package br.com.ntconsultant.instant.cooperative.voting.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "kafka")
public class KafkaVotingProperties {

    private String bootstrapServers;
    private String consumerTopicVoting;
    private String producerTopic;
    private String groupId;
}
