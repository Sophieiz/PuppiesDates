# 1. Etapa de compilación (Build)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos todo lo que está dentro de la carpeta con espacios directamente a /app
COPY "Cafeteriatalleres insert 23-06-26/" ./

# Como el pom.xml ya queda directo en /app, compilamos aquí mismo
RUN mvn clean package -DskipTests

# 2. Etapa de ejecución (Server con Payara)
FROM payara/server-full:6.2023.8-jdk17

# Copiamos el archivo .war resultante al directorio de despliegue de Payara
COPY --from=build /app/target/*.war $PAYARA_DIR/glassfish/domains/domain1/autodeploy/ROOT.war

EXPOSE 8080
