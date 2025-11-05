package com.damvih.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.damvih.dao", "com.damvih.services"})
public class AppConfig {

}
