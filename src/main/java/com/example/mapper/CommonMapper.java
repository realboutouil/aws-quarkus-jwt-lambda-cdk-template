package com.example.mapper;

import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Mapper(
        config = QuarkusMappingConfig.class
)
public class CommonMapper {

    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm a";

    public String uuidToId(UUID id) {
        if (Objects.isNull(id)) return null;
        return id.toString();
    }

    public UUID idToUUID(String id) {
        if (Objects.isNull(id)) return null;
        return UUID.fromString(id);
    }

    public OffsetDateTime dateToIdOffset(Date date) {
        if (Objects.isNull(date)) return null;
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toOffsetDateTime();
    }

    public String instantToString(Instant instant) {
        if (Objects.isNull(instant)) return null;
        return DateTimeFormatter
                .ofPattern(DATE_FORMAT)
                .withZone(ZoneId.systemDefault())
                .format(instant);
    }

    public String offsetToString(OffsetDateTime offset) {
        if (Objects.isNull(offset)) return null;
        return DateTimeFormatter
                .ofPattern(DATE_FORMAT)
                .format(offset);
    }

    public String dateToString(Date date) {
        if (Objects.isNull(date)) return null;
        return DateTimeFormatter
                .ofPattern(DATE_FORMAT)
                .withZone(ZoneId.systemDefault())
                .format(date.toInstant());
    }
}