package br.com.ntconsultant.instant.cooperative.voting.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class FormatterUtilTest {

    @Test
    void formatterLocalDateTimeBy() {
        LocalDateTime data = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String textDate = data.format(formatter);

        String value = FormatterUtil.formatterLocalDateTimeBy(data);
        assertEquals(textDate, value);
    }

    @Test
    void formatterLocalDateBy() {
        LocalDate data = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String textDate = data.format(formatter);

        String value = FormatterUtil.formatterLocalDateBy(data);
        assertEquals(textDate, value);
    }

    @Test
    void testFormatterLocalDateByThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FormatterUtil.formatterLocalDateBy(null));

        assertTrue(exception.getMessage().contains("invalid and/or non-existent"));
    }

    @Test
    void testFormatterLocalDateTimeByThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FormatterUtil.formatterLocalDateTimeBy(null));

        assertTrue(exception.getMessage().contains("invalid and/or non-existent"));
    }

    @Test
    void formatterLocalDateTimeFrom() {
        LocalDate data = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String textoDataEsperado = data.format(formatter);

        String valorDataTextoResult = FormatterUtil.formatterLocalDateBy(data);

        assertEquals(textoDataEsperado, valorDataTextoResult);
    }
}
