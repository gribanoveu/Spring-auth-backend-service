package com.github.gribanoveu.cuddle.entities.services.locale;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Evgeny Gribanov
 * @version 18.01.2024
 */
@Service
@RequiredArgsConstructor
public class LocaleServiceImpl implements LocaleService {
    private final MessageSource messageSource;

    @Override
    public String getMessage(String key) {
        var locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }
}
