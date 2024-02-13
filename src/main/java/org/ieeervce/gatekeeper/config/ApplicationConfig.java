package org.ieeervce.gatekeeper.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    /**
     * A default modelmapper configuration.
     * @return modelmapper instance
     */
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
