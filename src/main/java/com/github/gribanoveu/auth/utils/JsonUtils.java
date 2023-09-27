package com.github.gribanoveu.auth.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 */
@Slf4j
@Component
public class JsonUtils {
    public String convertDtoToJson(Object dto) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            log.error(e.getLocalizedMessage());
        }
        return "";
    }
}