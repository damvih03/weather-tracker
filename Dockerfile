FROM gradle:9.2.1-jdk25 AS build
WORKDIR /app
COPY . .
RUN gradle clean build --build-cache

FROM tomcat:11.0.14-jdk25
WORKDIR /usr/local/tomcat
RUN rm -rf webapps/*
COPY --from=build /app/build/libs/*.war webapps/ROOT.war
CMD ["catalina.sh", "run"]