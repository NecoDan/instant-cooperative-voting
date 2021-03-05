package br.com.ntconsultant.instant.cooperative.voting.service;

import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * @author Daniel Santos
 */
public interface IGenerateVotingService {

    Mono<Void> openingSessionVoting(String idPauta, Instant endSession);
}
