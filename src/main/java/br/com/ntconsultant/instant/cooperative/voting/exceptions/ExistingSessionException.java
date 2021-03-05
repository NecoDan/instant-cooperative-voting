package br.com.ntconsultant.instant.cooperative.voting.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 */
public class ExistingSessionException extends HttpException {

    public ExistingSessionException(String message) {
        super(String.format("There is already a voting session for the agenda: %S", message));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.EXPECTATION_FAILED;
    }
}
