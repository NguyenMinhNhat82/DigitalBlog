
FROM openjdk:17-jdk-alpine
MAINTAINER baeldung.com
COPY target/DigitalBlog-1.0-SNAPSHOT.jar digitalblog.jar
ENTRYPOINT ["java","-jar","/digitalblog.jar"]