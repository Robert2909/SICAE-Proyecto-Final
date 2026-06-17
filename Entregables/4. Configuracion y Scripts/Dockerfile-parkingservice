# Usamos una imagen oficial
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# pasamos el archivo jar generado por maven al contenedor
COPY target/*.jar app.jar

EXPOSE 8084

# por ultimo se ejecuta aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
