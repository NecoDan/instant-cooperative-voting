package br.com.ntconsultant.instant.cooperative.voting.events;

import br.com.ntconsultant.instant.cooperative.voting.model.Session;
import org.springframework.context.ApplicationEvent;

/**
 * @author Daniel Santos
 */
public class SessionCreatedEvent extends ApplicationEvent {
    public SessionCreatedEvent(Session source) {
        super(source);
    }
}
