package ru.job4j.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public final class JSONUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JSONUtils.class.getName());

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JSONUtils() {}

    public static String serialize(Object object) {
        String json = "";
        try {
            json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            LOG.debug("Failed to serialize: {}, {}", object, e.getCause());
        }
        return json;
    }

    public static <T> Optional<T> deserialize(String json, Class<T> cls) {
        Optional<T> object = Optional.empty();
        try {
            object = Optional.ofNullable(MAPPER.readValue(json, cls));
        } catch (Exception e) {
            LOG.debug("Failed to deserialize: {}, {}", object, e.getCause());
        }
        return object;
    }


}
