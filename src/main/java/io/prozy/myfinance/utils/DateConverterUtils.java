package io.prozy.myfinance.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateConverterUtils {

    public static LocalDateTime longToLdt(Long dateTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.systemDefault());
    }
}