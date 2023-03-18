package io.github.heartalborada_del.newBingAPI.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.Random;

import static java.time.format.DateTimeFormatter.*;
import static java.time.temporal.ChronoField.*;

public class Utils {
    private static final DateTimeFormatter OFFSET_DATE_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_DATE)
            .appendLiteral('T')
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .optionalStart()
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .optionalStart()
            .appendOffsetId()
            .toFormatter();

    /**
     * Returns the current date and time as a string formatted with an offset.
     *
     * @return the current date and time as a string formatted with an offset.
     */
    public static String getNowTime() {
        ZonedDateTime date = ZonedDateTime.now();
        return date.format(OFFSET_DATE_FORMATTER);
    }

    /**
     * Generates a random string of the specified length.
     *
     * @param length the length of the random string.
     * @return a random string of the specified length.
     */
    public static String randomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length() - 1);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
