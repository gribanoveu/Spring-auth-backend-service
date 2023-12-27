package com.github.gribanoveu.cuddle.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * @author Evgeny Gribanov
 * @version 25.12.2023
 */
@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Spring Api",
        description = "Описание Api", version = "0.1.0",
        contact = @Contact(
                name = "Evgeny Gribanov",
                email = "egribanov@yandex.com",
                url = "https://codecow.pw"
        )
))
@SecurityScheme(name = "JWT", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class SwaggerConfig { }
