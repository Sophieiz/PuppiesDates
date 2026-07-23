# 1. Etapa de compilación (Build)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

# Usamos wildcard (*) para que encuentre la carpeta sin importar los espacios
WORKDIR /app/Cafeteria*
RUN mvn clean package -DskipTests

# 2. Etapa de ejecución (Server con Payara)
FROM payara/server-full:6.2023.8-jdk17

# Copiamos el archivo .war generado
COPY --from=build /app/Cafeteria*/target/*.war $PAYARA_DIR/glassfish/domains/domain1/autodeploy/ROOT.war

EXPOSE 8080
