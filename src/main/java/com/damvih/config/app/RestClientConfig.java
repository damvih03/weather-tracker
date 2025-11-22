package com.damvih.config.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Configuration
public class RestClientConfig {

    private static final String MAIN_WEATHER_URL = "https://api.openweathermap.org";

    @Value("${weather-api-key}")
    private String weatherApiKey;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(MAIN_WEATHER_URL)
                .requestInterceptor((request, body, execution) -> {
                    URI uri = UriComponentsBuilder.fromUri(request.getURI())
                            .queryParam("appid", weatherApiKey)
                            .build(true)
                            .toUri();

                    HttpRequest modifiedRequest = new HttpRequestWrapper(request) {
                        @Override
                        public URI getURI() {
                            return uri;
                        }
                    };
                    return execution.execute(modifiedRequest, body);
                })
                .build();
    }

}
