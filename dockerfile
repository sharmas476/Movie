FROM maven:3.8.4-jdk-11-alpine as builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package
FROM java:11
COPY --from=builder /usr/src/app/target/movie-1.0.0.jar /usr/app/movie-1.0.0.jar
ENTRYPOINT ["java", "-jar", "/usr/app/movie-1.0.0.jar"]
EXPOSE 80