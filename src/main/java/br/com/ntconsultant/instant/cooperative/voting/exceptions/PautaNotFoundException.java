package br.com.ntconsultant.instant.cooperative.voting.exceptions;

import org.springframework.http.HttpStatus;

public class PautaNotFoundException extends HttpException {

    public PautaNotFoundException(String message) {
        super(String.format("Pauta '%s' not found.", message));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
