package com.damvih.configs;

import com.damvih.dto.SessionDto;
import com.damvih.entities.Session;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = {"com.damvih.dao", "com.damvih.services"})
@PropertySource("classpath:application.properties")
@Import({TaskSchedulerConfig.class})
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Session.class, SessionDto.class).addMappings(mapper -> {
            mapper.map(Session::getUser, SessionDto::setUserDto);
        });

        return modelMapper;
    }

}
