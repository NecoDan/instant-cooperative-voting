package br.com.ntconsultant.instant.cooperative.voting.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 */
public abstract class HttpException extends RuntimeException {

    HttpException(String message) {
        super(message);
    }

    public abstract HttpStatus getHttpStatus();
}
