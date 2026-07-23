# 1. Etapa de compilación (Build)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. Etapa de ejecución (Server compatible con Jakarta EE 10 / GlassFish)
FROM payara/server-full:6.2023.8-jdk17
COPY --from=build /app/target/*.war $PAYARA_DIR/glassfish/domains/domain1/autodeploy/ROOT.war
EXPOSE 8080
