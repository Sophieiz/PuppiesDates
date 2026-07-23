# ---- Etapa 1: Compilar el proyecto con Ant ----
FROM eclipse-temurin:17-jdk AS build

RUN apt-get update && apt-get install -y ant && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY . .

WORKDIR "/app/Cafeteriatalleres insert 23-06-26"
RUN ant clean dist

# ---- Etapa 2: Payara (GlassFish) para ejecutar el .war ----
FROM payara/server-full:6.2024.6-jdk17

COPY --from=build "/app/Cafeteriatalleres insert 23-06-26/dist/"*.war $DEPLOY_DIR/ROOT.war
# trigger redeploy
