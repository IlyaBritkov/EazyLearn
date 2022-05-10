package com.eazylearn.util;

import lombok.NoArgsConstructor;
import org.mapstruct.Named;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Convertor {

    @Named("uuidToString")
    public static String uuidToString(UUID uuid) {
        return uuid.toString();
    }

    @Named("stringToUUID")
    public static UUID stringToUUID(String string) {
        return UUID.fromString(string);
    }
}
