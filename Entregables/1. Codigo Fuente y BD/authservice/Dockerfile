# Usamos una imagen oficial y ligera de Java 17
FROM eclipse-temurin:17-jdk-alpine

# Creamos una carpeta dentro del contenedor donde vivirá nuestra app
WORKDIR /app

# Copiamos el archivo .jar que genera NetBeans al darle "Clean and Build"
# Si NetBeans genera el .jar distinto, cambia el nombre del archivo .jar siguiente
# El ojo estaba feo jeje
COPY target/authservice-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto 8081 que acordamos en la Fase 0
EXPOSE 8081

# Le decimos al contenedor cómo arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
