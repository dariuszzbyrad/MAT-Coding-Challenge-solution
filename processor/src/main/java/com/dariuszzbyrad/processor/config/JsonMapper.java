package com.dariuszzbyrad.processor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JsonMapper {

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
