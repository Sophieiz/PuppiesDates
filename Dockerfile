# 1. Etapa de compilación (Build)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

# Entramos a la subcarpeta usando comillas dobles normales
WORKDIR "/app/Cafeteriatalleres insert 23-06-26"
RUN mvn clean package -DskipTests

# 2. Etapa de ejecución (Server con Payara)
FROM payara/server-full:6.2023.8-jdk17

# Usamos la sintaxis JSON con comillas dobles para que Docker entienda los espacios
COPY --from=build ["/app/Cafeteriatalleres insert 23-06-26/target/*.war", "$PAYARA_DIR/glassfish/domains/domain1/autodeploy/ROOT.war"]

EXPOSE 8080
