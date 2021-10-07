package com.a506.mirinae;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
@EnableJpaAuditing
@EnableSwagger2
public class MirinaeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MirinaeApplication.class, args);
	}

}
