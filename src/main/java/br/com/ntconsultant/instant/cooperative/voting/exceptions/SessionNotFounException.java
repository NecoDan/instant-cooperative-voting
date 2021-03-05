package br.com.ntconsultant.instant.cooperative.voting.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 */
public class SessionNotFounException extends HttpException {

    public SessionNotFounException(String message) {
        super(String.format("Session not found for pauta: %s.", message));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
