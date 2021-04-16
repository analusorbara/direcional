package helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import models.Pld;

import java.text.SimpleDateFormat;

public class JsonHelper {
    public static String objectToJsonString(Pld pld) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        objectMapper.setDateFormat(df);
        return objectMapper.writeValueAsString(pld);
    }

    public static <T> T jsonStringToObject(String json, Class<T> valueType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        objectMapper.setDateFormat(df);
        return objectMapper.readValue(json, valueType);
    }
}
