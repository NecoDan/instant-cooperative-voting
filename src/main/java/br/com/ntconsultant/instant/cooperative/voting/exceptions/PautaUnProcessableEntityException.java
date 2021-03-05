package br.com.ntconsultant.instant.cooperative.voting.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 */
public class PautaUnProcessableEntityException extends HttpException {

    public PautaUnProcessableEntityException(String message) {
        super(String.format("Could not process the Pauta: %s.", message));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

}
