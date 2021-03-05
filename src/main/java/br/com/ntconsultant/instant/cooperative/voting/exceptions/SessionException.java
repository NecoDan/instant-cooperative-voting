package br.com.ntconsultant.instant.cooperative.voting.exceptions;

import lombok.NoArgsConstructor;

/**
 * @author Daniel Santos
 */
@NoArgsConstructor
public class SessionException extends RuntimeException {
    public SessionException(String s) {
        super(s);
    }

    public SessionException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SessionException(Throwable throwable) {
        super(throwable);
    }

    public SessionException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
