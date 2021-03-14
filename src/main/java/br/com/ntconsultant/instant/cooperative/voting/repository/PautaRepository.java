package br.com.ntconsultant.instant.cooperative.voting.repository;

import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import io.swagger.v3.oas.models.info.License;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author Daniel Santos
 */
public interface PautaRepository extends ReactiveMongoRepository<Pauta, String> {

}
