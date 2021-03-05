package br.com.ntconsultant.instant.cooperative.voting.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 */
public class VoterNotFoundException extends HttpException {

    public VoterNotFoundException(String message) {
        super(String.format("Voter '%s' not found.", message));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
