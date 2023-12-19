# Etapa de compilação (build-stage)
FROM maven-jdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src /app/src
RUN mvn clean package -DskipTests

# Etapa de produção
FROM java-17-debian
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY --from=builder /app/target/animal.hotel.spring-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080