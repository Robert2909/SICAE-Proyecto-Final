-- Inicialización de BD
CREATE DATABASE IF NOT EXISTS `sicaeparking`;
USE `sicaeparking`;

DROP TABLE IF EXISTS `ticket`;
DROP TABLE IF EXISTS `cajon`;
DROP TABLE IF EXISTS `configuracion`;

-- luego se configura la tabla (limites y costos)
CREATE TABLE `configuracion` (
  `idConfiguracion` int NOT NULL AUTO_INCREMENT,
  `capacidadTotal` int NOT NULL,
  `costoHora` decimal(10,2) NOT NULL,
  `costoMinuto` decimal(10,2) NOT NULL,
  PRIMARY KEY (`idConfiguracion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- después la configuración inicial.
INSERT INTO `configuracion` (`capacidadTotal`, `costoHora`, `costoMinuto`) VALUES (50, 15.00, 0.25);

-- se representan los lugares disponibles con la tabla cajon.
CREATE TABLE `cajon` (
  `idEspacio` int NOT NULL AUTO_INCREMENT,
  `ocupado` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`idEspacio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Metemos los 50 cajones (desocupados)
INSERT INTO `cajon` (`ocupado`) VALUES
(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),
(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),
(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),
(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),
(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0'),(b'0');

-- esta es la tabla principal en la que se registran los tickets
CREATE TABLE `ticket` (
  `idTicket` int NOT NULL AUTO_INCREMENT,
  `idUsuario` int NOT NULL,
  `placa` varchar(7) NOT NULL,
  `horaEntrada` datetime NOT NULL,
  `horaSalida` datetime DEFAULT NULL,
  `tiempoTotalMinutos` int DEFAULT NULL,
  `idEspacio` int NOT NULL,
  `tarifaHora` decimal(10,2) NOT NULL,
  `horasCobradas` int DEFAULT NULL,
  `costoTotal` decimal(10,2) DEFAULT NULL,
  `estatus` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`idTicket`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;