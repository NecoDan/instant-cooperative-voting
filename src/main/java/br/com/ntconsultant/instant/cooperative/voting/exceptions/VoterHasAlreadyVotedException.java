package br.com.ntconsultant.instant.cooperative.voting.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 */
public class VoterHasAlreadyVotedException extends HttpException {

    public VoterHasAlreadyVotedException(String message) {
        super(String.format("Voter '%s' has already voted .", message));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.EXPECTATION_FAILED;
    }
}
