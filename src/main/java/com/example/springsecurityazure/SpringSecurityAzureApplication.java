package com.example.springsecurityazure;

import com.example.springsecurityazure.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class SpringSecurityAzureApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAzureApplication.class, args);
	}

}
