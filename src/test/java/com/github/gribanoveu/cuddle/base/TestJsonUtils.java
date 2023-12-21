package com.github.gribanoveu.cuddle.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Evgeny Gribanov
 * @version 12.09.2023
 */
@Component
@Slf4j
public class TestJsonUtils {
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

    public LocalDateTime calculateTokenDateTimeFromDate(Date exp, Duration tokenLifetime) {
        // convert Date to LocalDateTime
        Instant instant = exp.toInstant();
        LocalDateTime expDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        // convert Duration to minutes
        long tokenLifetimeMinutes = tokenLifetime.toMinutes();

        // calculate refreshDateTime
        return expDateTime.plusMinutes(tokenLifetimeMinutes);
    }

    public String getJsonValueFromMvcResult(MvcResult result, String value) {
        try {
            var responseJson = result.getResponse().getContentAsString();
            var responseNode = new ObjectMapper().readTree(responseJson);
            return responseNode.get(value).asText();
        } catch (Exception e ) {
            log.error(e.getLocalizedMessage());
        }
        return "";
    }
}
