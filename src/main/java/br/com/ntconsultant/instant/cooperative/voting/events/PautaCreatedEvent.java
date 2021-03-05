package br.com.ntconsultant.instant.cooperative.voting.events;

import br.com.ntconsultant.instant.cooperative.voting.model.Pauta;
import org.springframework.context.ApplicationEvent;

/**
 * @author Daniel Santos
 */
public class PautaCreatedEvent extends ApplicationEvent {
    public PautaCreatedEvent(Pauta source) {
        super(source);
    }
}
