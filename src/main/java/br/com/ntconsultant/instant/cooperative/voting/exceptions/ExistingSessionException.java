package br.com.ntconsultant.instant.cooperative.voting.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 */
public class ExistingSessionException extends HttpException {

    private static final long serialVersionUID = 2976467110994522770L;

    public ExistingSessionException(String message) {
        super(String.format("There is already a voting session for the pauta: %S", message));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.EXPECTATION_FAILED;
    }
}
