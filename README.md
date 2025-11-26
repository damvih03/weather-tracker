# Weather tracker

Created according to the [technical specifications](https://zhukovsd.github.io/java-backend-learning-course/projects/weather-viewer/) presented in this [course](https://zhukovsd.github.io/java-backend-learning-course/).

## Overview

A web application for viewing current weather. Allows you to create an account and add locations (one or multiple) where you want to track the weather.

## Tech stack

### Backend

- Java
- Gradle
- Spring Framework (MVC, ORM, Test)
- Thymeleaf
- Hibernate
- PostgreSQL, H2 (for tests)
- Flyway
- ModelMapper
- Lombok
- JUnit5
- Mockito

### Frontend

- HTML/CSS
- Bootstrap 5

## Requirements

- Java 17+
- Gradle 9.x
- Tomcat 11

## Installation

1. Clone repository.
```
https://github.com/damvih03/weather-tracker.git
```
2. Register and get your OpenWeather API key [here](https://openweathermap.org/).
3. Add your API key to `src/main/resources/application.properties`.
```
...
weather-api-key=
```
4. Build docker image.
```
docker build -t weather-tracker
```
5. Start the application using docker сompose.
```
docker compose up
```
6. Open in browser: http://localhost:8080/.