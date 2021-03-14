package br.com.ntconsultant.instant.cooperative.voting.util;

import org.slf4j.MDC;

import java.util.Date;
import java.util.Objects;

/**
 * @author Daniel Santos
 * {@link br.com.ntconsultant.instant.cooperative.voting.util.Utils}
 * @since 05/03/2021
 */
public final class Utils {

    private Utils() {

    }

    public static void timeElapsed() {
        String timeElapsed = MDC.get("startTime");
        if (Objects.nonNull(timeElapsed) && timeElapsed.length() > 0) {
            MDC.put("timeElapsed", String.valueOf(System.currentTimeMillis() - Long.parseLong(timeElapsed)));
        }
    }
}
