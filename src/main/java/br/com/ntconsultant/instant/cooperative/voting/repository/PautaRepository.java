package br.com.ntconsultant.instant.cooperative.voting.repository;

import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PautaRepository extends ReactiveMongoRepository<Pauta, String> {

}
