package com.damvih.configs;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Bean
    public Flyway flyway(DataSource dataSource, @Value("${schema}") String schemaName) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .defaultSchema(schemaName)
                .load();

        flyway.migrate();
        return flyway;
    }

}
