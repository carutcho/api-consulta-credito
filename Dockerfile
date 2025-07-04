FROM openjdk:11 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

FROM openjdk:11
WORKDIR /app

COPY --from=build /app/target/api-pedidos-0.0.1-SNAPSHOT.jar app.jar

# Expõe as portas:
# 8080 para a aplicação (ou conforme configurado no seu application.properties)
# 5005 para debug remoto, se necessário
EXPOSE 8080

# Comando de entrada que inicia a aplicação
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-DEV}", "-jar", "app.jar"]
