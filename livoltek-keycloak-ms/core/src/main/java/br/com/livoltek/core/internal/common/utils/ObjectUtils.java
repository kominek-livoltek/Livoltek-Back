package br.com.livoltek.core.internal.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class ObjectUtils {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static <T> T deserializar(String object, TypeReference<T> type) {
        if (Objects.isNull(object)) return null;
        try {
            return OBJECT_MAPPER.readValue(object, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Não foi possível deserializar o objeto.");
        }
    }

    public static <T> List<T> deserializarLista(String object, TypeReference<?> type) {
        if (Objects.isNull(object)) return null;
        try {
            return (List<T>) OBJECT_MAPPER.readValue(object, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Não foi possível deserializar o objeto.");
        }
    }

    public static String serializar(Object object) {
        if (Objects.isNull(object) || ((List<?>) object).isEmpty()) return null;
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Não foi possível serializar o objeto.");
        }
    }

    public static UUID getUUIDFromLocationHeader(String location) {
        return UUID.fromString(location.substring(location.lastIndexOf('/') + 1));
    }

    public static boolean isValidEmail(String email) {
        String pattern = "^(.+)@(\\S+)$";
        return Pattern.compile(pattern)
                .matcher(email)
                .matches();
    }
}
