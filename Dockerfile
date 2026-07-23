# ---- Etapa 1: Compilar el proyecto con javac ----
FROM tomcat:10.1-jdk17 AS build

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
      -cp "web/WEB-INF/lib/*:/usr/local/tomcat/lib/*" \
      -d build/WEB-INF/classes \
      @/tmp/sources.txt; \
    cd build && jar -cf ../dist/ROOT.war .

# ---- Etapa 2: Tomcat liviano ----
FROM tomcat:10.1-jdk17-temurin-jammy

COPY --from=build /app/PuppiesDate/dist/ROOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
