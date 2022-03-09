FROM amazoncorretto:11
COPY target/movie-1.0.0.jar movie-1.0.0.jar
ENTRYPOINT ["java","-jar","/movie-1.0.0.jar"]