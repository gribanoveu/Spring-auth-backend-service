package com.github.gribanoveu.cuddle;

import com.github.gribanoveu.cuddle.config.RsaProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableConfigurationProperties({RsaProperties.class})
@SpringBootApplication
public class CuddleApplication {
	public static void main(String[] args) {
		SpringApplication.run(CuddleApplication.class, args);
	}
}
