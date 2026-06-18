# Usamos una imagen oficial y ligera de Java 17
FROM eclipse-temurin:17-jdk-alpine

# Creamos una carpeta dentro del contenedor donde vivirá nuestra app
WORKDIR /app

COPY target/authservice-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
