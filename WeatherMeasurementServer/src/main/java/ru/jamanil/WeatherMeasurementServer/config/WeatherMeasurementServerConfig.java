package ru.jamanil.WeatherMeasurementServer.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Victor Datsenko
 * 01.11.2022
 */
@Configuration
public class WeatherMeasurementServerConfig {
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
