package br.com.ntconsultant.instant.cooperative.voting.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Daniel Santos
 * @since 10/03/2021
 */
class RandomUtilTest {

    @Test
    void generateRandomValueUntil() {
        int limit = 100;
        int result = RandomUtil.generateRandomValueUntil(limit);
        assertTrue((result > 0) && (result <= 100));
    }
}