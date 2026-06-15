# Usamos una imagen oficial y ligera de Java 17 (asegúrate de que coincida con la versión de tu pom.xml)
FROM eclipse-temurin:17-jdk-alpine

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el archivo .jar generado por Maven hacia el contenedor
COPY target/*.jar app.jar

# Exponemos el puerto 8084 que configuramos en el application.properties
EXPOSE 8084

# Comando para ejecutar la aplicación cuando el contenedor inicie
ENTRYPOINT ["java", "-jar", "app.jar"]
