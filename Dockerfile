# ---- Etapa 1: Compilar el proyecto directo con javac (sin depender del harness de NetBeans/Ant) ----
FROM payara/server-full:6.2024.6-jdk17 AS build

USER root
WORKDIR /app
COPY . .
WORKDIR /app/PuppiesDate

RUN set -e; \
    rm -rf build dist; \
    mkdir -p build dist build/WEB-INF/classes; \
    cp -r web/. build/; \
    find src/java -name "*.java" > /tmp/sources.txt; \
    javac -encoding UTF-8 \
      -cp "web/WEB-INF/lib/*:/opt/payara/appserver/glassfish/modules/*" \
      -d build/WEB-INF/classes \
      @/tmp/sources.txt; \
    cd build && jar -cf ../dist/ROOT.war .

# ---- Etapa 2: Payara (GlassFish) para ejecutar el .war ----
FROM payara/server-full:6.2024.6-jdk17

COPY --from=build /app/PuppiesDate/dist/ROOT.war $DEPLOY_DIR/ROOT.war
