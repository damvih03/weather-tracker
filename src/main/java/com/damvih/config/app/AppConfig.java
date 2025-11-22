package com.damvih.config.app;

import com.damvih.dto.session.SessionDto;
import com.damvih.entity.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = {"com.damvih.dao", "com.damvih.service"})
@PropertySource("classpath:application.properties")
@Import({RestClientConfig.class, TaskSchedulerConfig.class})
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Session.class, SessionDto.class).addMappings(mapper -> {
            mapper.map(Session::getUser, SessionDto::setUserDto);
        });

        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
