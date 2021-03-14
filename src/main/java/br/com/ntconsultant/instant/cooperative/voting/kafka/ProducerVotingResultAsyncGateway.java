package br.com.ntconsultant.instant.cooperative.voting.kafka;

import br.com.ntconsultant.instant.cooperative.voting.kafka.resource.VotingResultMessage;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.properties.KafkaVotingProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProducerVotingResultAsyncGateway implements IProducerVotingResultAsyncGateway {

    private final KafkaVotingProperties kafkaVotingProperties;
    private final KafkaTemplate<String, String> kafkaTemplateVotingResult;
    private final ObjectMapper objectMapper;

    @Override
    public void send(Pauta pauta) {
        final String message = toJsonStringFrom(pauta);
        final String topic = kafkaVotingProperties.getProducerTopic();
        this.kafkaTemplateVotingResult.send(topic, message);
        log.info("ProducerVotingResultAsyncGateway: Producer message - topic: {} process message {}", topic, message);
    }

    private String toJsonStringFrom(final Pauta pauta) {
        try {
            return this.objectMapper.writeValueAsString(getVotingResulMessageFrom(pauta));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private VotingResultMessage getVotingResulMessageFrom(Pauta pauta) {
        return VotingResultMessage
                .builder()
                .title(pauta.getTitle())
                .status(pauta.toStringTypeStatusSession())
                .voteModels(pauta.generateVoteTotalizers())
                .build();
    }
}
