package com.github.gribanoveu.cuddle.config;

import com.github.gribanoveu.cuddle.utils.YamlPropertiesLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.List;

/**
 * @author Evgeny Gribanov
 * @version 18.01.2024
 */
@Configuration
public class MessageConfig {

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:locales/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setPropertiesPersister(new YamlPropertiesLoader());
        messageSource.setFileExtensions(List.of(".yml", ".yaml"));
        return messageSource;
    }
}
