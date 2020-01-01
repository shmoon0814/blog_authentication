FROM openjdk:11-jdk
VOLUME /tmp
EXPOSE 8761
ARG JAR_FILE=target/authentication-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} authentication.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/authentication.jar"]