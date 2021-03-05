package br.com.ntconsultant.instant.cooperative.voting.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @author Daniel Santos
 * {@link br.com.ntconsultant.instant.cooperative.voting.util.DateUtil} classe útil que por motivação e finalidade múltiplas operações envolvendo datas sejam elas do tipo
 * {@link LocalDate}, {@link LocalDateTime}, {@link Date}, {@link Calendar}
 * @since 05/03/2021
 */
public final class DateUtil {

    private static final String VALIDATION_MESSAGE = "Parameter referring to {DATE}, is invalid and/or non-existent.";

    private DateUtil() {

    }

    public static LocalDate getLocalDateFrom(Instant instant) {
        if (Objects.isNull(instant))
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime getLocalDateTimeFrom(Instant instant) {
        if (Objects.isNull(instant))
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
