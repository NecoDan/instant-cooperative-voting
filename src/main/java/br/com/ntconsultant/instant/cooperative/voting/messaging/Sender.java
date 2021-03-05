package br.com.ntconsultant.instant.cooperative.voting.messaging;

import br.com.ntconsultant.instant.cooperative.voting.dto.VotingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Sender {

    private final KafkaProperties kafkaProperties;
    private final KafkaTemplate<String, VotingResult> kafkaTemplateVotingResult;

}
