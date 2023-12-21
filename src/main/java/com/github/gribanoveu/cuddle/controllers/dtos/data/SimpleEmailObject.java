package com.github.gribanoveu.cuddle.controllers.dtos.data;

import java.util.Map;

/**
 * @author Evgeny Gribanov
 * @version 28.11.2023
 */

public record SimpleEmailObject(
    String from,
    String to,
    String subject,
    String templateLocation,
    Map<String, Object> context
) {}
