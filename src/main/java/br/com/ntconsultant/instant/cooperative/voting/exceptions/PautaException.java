package br.com.ntconsultant.instant.cooperative.voting.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PautaException extends Exception {
    public PautaException(String s) {
        super(s);
    }

    public PautaException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public PautaException(Throwable throwable) {
        super(throwable);
    }

    public PautaException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
