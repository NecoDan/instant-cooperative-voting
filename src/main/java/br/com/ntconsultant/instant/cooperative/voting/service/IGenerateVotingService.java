package br.com.ntconsultant.instant.cooperative.voting.service;

import br.com.ntconsultant.instant.cooperative.voting.exceptions.PautaException;
import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import br.com.ntconsultant.instant.cooperative.voting.model.Vote;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * @author Daniel Santos
 */
public interface IGenerateVotingService {

    Mono<Pauta> create(Pauta pauta) throws PautaException;

    Mono<Void> openingSessionVoting(String idPauta, Instant endSession);

    Mono<Void> vote(String idPauta, Vote vote);
}
