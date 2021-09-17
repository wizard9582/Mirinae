FROM openjdk:11 AS builder
WORKDIR /backend
COPY . .
RUN chmod +x ./gradlew

RUN rm -rf src/test
RUN ./gradlew clean build

FROM adoptopenjdk:11-jdk
COPY --from=builder ./backend/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./app.jar"]