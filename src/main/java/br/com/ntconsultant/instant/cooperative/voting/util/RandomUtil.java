package br.com.ntconsultant.instant.cooperative.voting.util;

import java.util.Random;

/**
 * @author Daniel Santos
 */
public final class RandomUtil {

    private static final int LIMIT_MIN_RANDOM_INTEGER = 1;

    private RandomUtil() {
    }

    private static Random getRandom() {
        return new Random();
    }

    public static Integer generateRandomValueUntil(int max) {
        return LIMIT_MIN_RANDOM_INTEGER + getRandom().nextInt(max);
    }
}
