# 1. Etapa de compilación (Build)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

# Usamos el comodín para entrar a la carpeta sin importar el nombre exacto ni los espacios
WORKDIR /app/Cafeteria*
RUN mvn clean package -DskipTests

# 2. Etapa de ejecución (Server con Payara)
FROM payara/server-full:6.2023.8-jdk17

# Copiamos el archivo .war resultante usando el comodín
COPY --from=build /app/Cafeteria*/target/*.war $PAYARA_DIR/glassfish/domains/domain1/autodeploy/ROOT.war

EXPOSE 8080
