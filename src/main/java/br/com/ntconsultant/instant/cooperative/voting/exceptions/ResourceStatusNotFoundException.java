package br.com.ntconsultant.instant.cooperative.voting.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceStatusNotFoundException extends HttpException {

    public ResourceStatusNotFoundException(String message) {
        super(String.format("Resource not found: %s.", message));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
