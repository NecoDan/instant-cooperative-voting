package br.com.ntconsultant.instant.cooperative.voting.kafka;

import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;

public interface IProducerVotingResultAsyncGateway {

    void send(Pauta pauta);

}
