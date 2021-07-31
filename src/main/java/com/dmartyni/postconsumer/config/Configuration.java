package com.dmartyni.postconsumer.config;

import com.dmartyni.postconsumer.errorhandler.RestTemplateResponseErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }
}
