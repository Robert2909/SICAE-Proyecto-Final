# Conclusión de la Fase 1

**LEAN TODO POR FAVOR, SI LO ESCRIBI CON MIS MANOS, NO TODO ES CHAT, TAMBIEN LOS COMENTARIOS DE CÓDIGO TODO TODO LEAN CON SUS OJOS**

Aqui está resumido todo lo que hice para la Fase 0 y la Fase 1 del proyecto SICAE, enfocado en establecer la arquitectura base, la seguridad y el servicio de autenticación.

## Estructura de Trabajo

Opté por utilizar una estructura de repositorio único en GitHub. En este repositorio acomodé todas las carpetas de los microservicios y las bases de datos de manera separada.
Para trabajar de forma eficiente en el entorno de desarrollo, es necesario que abran de manera individual la carpeta del microservicio correspondiente. Esto evita conflictos de dependencias en el entorno de NetBeans.

## Base de Datos

Acomodé los scripts proporcionados por el profe en la carpeta de base de datos.
Inicialicé la base de datos de usuarios en PostgreSQL, pero de todas formas inicialicen el suyo en sus propios administradores, les recomiendo pgAdmin 4.
Generé un registro de prueba adicional para validar el inicio de sesión. La consulta para insertar este usuario es la siguiente:

```sql
-- Este es un usuario de pruebas que ingrese, el usuario es admin1 y la contraseña es contra1
INSERT INTO "public"."usuario" VALUES (2, 'Usuario', 'De Pruebas', NULL, 'PRU-123', 'prueba@sicae.com', '2280000000', 'admin1', '$2a$12$CDx9dE4ZiRFWgUiP6E.NdumPjIdSgGSLQUEM514DQbwwghvtAWpby', '1', 1, 1, 3, '2026-06-10 12:00:00', NULL);
```

Por si acaso no saben como inicializar la base de datos, entren a pgAdmin 4 y denle a create database, le ponen sicaeUsuario y luego ejecutan todo el archivo sicaeUsuario.sql, asi como el que viene aqui, porque es el que tiene la consulta que yo agregué.

Añadi esta nueva consulta aparte porque el profe añadió una consulta propia con su propio usuario, pero esta tiene una contraseña cifrada, y no es descifrable, es un hash unidireccional.

Entonces, para hacer las pruebas simplemente no podemos saber su contraseña, por eso hice una en donde si la sabemos, pero lo puse en una consulta porque la parte de crear usuarios le toca a alguien más, entonces les dejé esa como un extra para pruebas.

## Inicialización del Servicio de Autenticación

Para ejecutar el servicio de autenticación, deben abrir el proyecto en NetBeans, ejecutar el proceso de compilación y limpieza, y posteriormente iniciar la aplicación. Configuré el servidor Tomcat para que inicie por defecto en el puerto 8081.
La ruta para enviar peticiones de prueba es POST http://localhost:8081/auth/login. Requiere enviar el nombre de usuario y la contraseña en formato JSON en el cuerpo de la petición.

## Seguridad y Arquitectura

Implementé la validación de contraseñas mediante la librería jbcrypt, utilizando el método checkpw para verificar matemáticamente el hash almacenado en la base de datos contra la contraseña ingresada.
Al verificar que los datos son correctos y el usuario se encuentra activo, programé el sistema para que genere un token de tipo JWT con una duración establecida de dos horas.
Deben utilizar este token en los demás microservicios para autorizar las peticiones. Para implementarlo, tienen que enviarlo en la cabecera de la petición con el formato Authorization Bearer seguido del token generado.
De todas formas, si quieren saber a nivel técnico TODO lo que hice, pueden checarse el dockerfile, el yml, y las clases del authservice, les dejé perfectamente comentado todo el código paso a paso para que puedan entender toda la arquitectura en un instante, y sino, pues me preguntan.

## Configuración de Docker

Incluí un archivo Dockerfile en el servicio de autenticación para generar su imagen correspondiente.
Agregué en la raíz del proyecto el archivo de configuración para Docker Compose, el cual se encarga de ejecutar simultáneamente la base de datos de PostgreSQL y el servicio de autenticación en una red virtual interna.
Si estas leyendo? Si leíste esto te regalo 2 pesos por hacerme caso de leer.
Dejé en este archivo de configuración un bloque comentado que corresponde al servicio de usuarios, el cual deberán habilitar una vez que finalicen su desarrollo.

## Nota importantísima

Si van a hacer algo con IA, POR FAVOR SANITICEN SU TRABAJO, hagan comentarios propios, comenten su propio código, NO UTILICEN CODIGO MAGICO, siempre básense por completo en lo que vimos en clase, no saquen librerías de quien sabe donde ni ocupen tecnologías que nunca vimos o sino nos van a tronar a todos.

Si ocupan IA, pidanle que vean mi authservice como referencia, ese mantiene mucha simpleza, es directo, y está perfectamente basado en lo que vimos en clases con el profe.

De todas formas, yo voy a sanitizar el proyecto una vez terminemos todo, para eliminar todo rastro de IA que se hayan dejado, pero por favor hagan el esfuerzo de sanitizar al máximo sus trabajos y sus fases.

Y HAGAN COMMITS Y PUSHES POR FAVOR
