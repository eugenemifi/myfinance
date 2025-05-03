package io.prozy.myfinance.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public abstract class DateMapper {

    @Named("localDateTimeToLong")
    public static Long ldtToLong(LocalDateTime ldt) {
        return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @Named("LongToLocalDateTime")
    public static LocalDateTime longToLdt(Long dateTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.systemDefault());
    }
}