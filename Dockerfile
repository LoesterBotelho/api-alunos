# ==========================================
# Stage 1 - Build
# ==========================================
FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

# Copia primeiro o pom.xml para aproveitar o cache do Docker
COPY pom.xml .

RUN mvn dependency:go-offline -B

# Copia o código-fonte
COPY src ./src

# Compila o projeto
RUN mvn clean package -DskipTests


# ==========================================
# Stage 2 - Runtime
# ==========================================
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copia o JAR gerado
COPY --from=build /app/target/*.jar app.jar

# Porta padrão do Spring Boot
EXPOSE 8080

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]