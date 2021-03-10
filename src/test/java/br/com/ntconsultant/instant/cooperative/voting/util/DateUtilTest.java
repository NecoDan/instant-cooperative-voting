package br.com.ntconsultant.instant.cooperative.voting.util;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Daniel Santos
 * @since 10/03/2021
 */
class DateUtilTest {

    @Test
    void getLocalDateFromInstant() {
        Instant instantParam = Instant.now();

        LocalDate dateResult = DateUtil.getLocalDateFrom(instantParam);
        String strDateResult = FormatterUtil.formatterLocalDateBy(dateResult);
        String strInstantResult = FormatterUtil.formatterLocalDateFrom(instantParam);

        assertTrue(Objects.nonNull(dateResult) && Objects.equals(strDateResult, strInstantResult));
    }

    @Test
    void getLocalDateTimeFromInstant() {
        Instant instantParam = Instant.now();

        LocalDateTime dateResult = DateUtil.getLocalDateTimeFrom(instantParam);
        String strDateResult = FormatterUtil.formatterLocalDateTimeBy(dateResult);
        String strInstantResult = FormatterUtil.formatterLocalDateTimeFrom(instantParam);

        assertTrue(Objects.nonNull(dateResult) && Objects.equals(strDateResult, strInstantResult));
    }
}
