package com.damvih.configs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:datasource.properties")
public class DataSourceConfig {

    public static final String PROPERTIES_FILE = "datasource.properties";

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig(PROPERTIES_FILE);
        return new HikariDataSource(config);
    }

}
