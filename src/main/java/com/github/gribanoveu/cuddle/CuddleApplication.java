package com.github.gribanoveu.cuddle;

import com.github.gribanoveu.cuddle.config.RsaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({RsaProperties.class})
public class CuddleApplication {
	public static void main(String[] args) {
		SpringApplication.run(CuddleApplication.class, args);
	}
}
