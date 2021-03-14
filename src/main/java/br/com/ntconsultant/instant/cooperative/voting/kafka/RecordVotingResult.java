package br.com.ntconsultant.instant.cooperative.voting.kafka;

import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecordVotingResult {

    private final ProducerVotingResultAsyncGateway producerVotingResultAsyncGateway;

    public void execute(final Pauta pauta) {
        producerVotingResultAsyncGateway.send(pauta);
    }
}
