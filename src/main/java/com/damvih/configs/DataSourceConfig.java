package com.damvih.configs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    public static final String PROPERTIES_FILE = "datasource.properties";

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig(PROPERTIES_FILE);
        return new HikariDataSource(config);
    }

}
