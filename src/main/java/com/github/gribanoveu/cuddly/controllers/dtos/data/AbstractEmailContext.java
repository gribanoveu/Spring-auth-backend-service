package com.github.gribanoveu.cuddly.controllers.dtos.data;

import lombok.Data;

import java.util.Map;

/**
 * @author Evgeny Gribanov
 * @version 28.11.2023
 */
@Data
public abstract class AbstractEmailContext {
    private String from;
    private String to;
    private String subject;
    private String templateLocation;
    private Map<String, Object> context;
}
