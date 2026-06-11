/*
 Navicat Premium Dump SQL

 Source Server         : MYSQL
 Source Server Type    : MySQL
 Source Server Version : 80045 (8.0.45)
 Source Host           : localhost:3306
 Source Schema         : sicaevehiculo

 Target Server Type    : MySQL
 Target Server Version : 80045 (8.0.45)
 File Encoding         : 65001

 Date: 26/05/2026 12:36:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for marca
-- ----------------------------
DROP TABLE IF EXISTS `marca`;
CREATE TABLE `marca`  (
  `idMarca` int NOT NULL AUTO_INCREMENT,
  `marca` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `estatus` bit(1) NOT NULL,
  PRIMARY KEY (`idMarca`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of marca
-- ----------------------------
INSERT INTO `marca` VALUES (1, 'Toyota', b'1');
INSERT INTO `marca` VALUES (2, 'Honda', b'1');
INSERT INTO `marca` VALUES (3, 'Nissan', b'1');
INSERT INTO `marca` VALUES (4, 'Chevrolet', b'1');
INSERT INTO `marca` VALUES (5, 'Ford', b'1');
INSERT INTO `marca` VALUES (6, 'Volkswagen', b'1');
INSERT INTO `marca` VALUES (7, 'Mazda', b'1');
INSERT INTO `marca` VALUES (8, 'Kia', b'1');
INSERT INTO `marca` VALUES (9, 'Hyundai', b'1');
INSERT INTO `marca` VALUES (10, 'BMW', b'1');
INSERT INTO `marca` VALUES (11, 'Mercedes-Benz', b'1');
INSERT INTO `marca` VALUES (12, 'Audi', b'1');
INSERT INTO `marca` VALUES (13, 'Suzuki', b'1');
INSERT INTO `marca` VALUES (14, 'Yamaha', b'1');
INSERT INTO `marca` VALUES (15, 'Italika', b'1');

-- ----------------------------
-- Table structure for modelo
-- ----------------------------
DROP TABLE IF EXISTS `modelo`;
CREATE TABLE `modelo`  (
  `idModelo` int NOT NULL AUTO_INCREMENT,
  `idMarca` int NOT NULL,
  `modelo` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `estatus` bit(1) NOT NULL,
  PRIMARY KEY (`idModelo`) USING BTREE,
  INDEX `idMarca`(`idMarca` ASC) USING BTREE,
  CONSTRAINT `modelo_ibfk_1` FOREIGN KEY (`idMarca`) REFERENCES `marca` (`idMarca`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of modelo
-- ----------------------------
INSERT INTO `modelo` VALUES (1, 1, 'Corolla', b'1');
INSERT INTO `modelo` VALUES (2, 1, 'Yaris', b'1');
INSERT INTO `modelo` VALUES (3, 1, 'Hilux', b'1');
INSERT INTO `modelo` VALUES (4, 2, 'Civic', b'1');
INSERT INTO `modelo` VALUES (5, 2, 'Accord', b'1');
INSERT INTO `modelo` VALUES (6, 2, 'CR-V', b'1');
INSERT INTO `modelo` VALUES (7, 3, 'Sentra', b'1');
INSERT INTO `modelo` VALUES (8, 3, 'Versa', b'1');
INSERT INTO `modelo` VALUES (9, 3, 'NP300', b'1');
INSERT INTO `modelo` VALUES (10, 4, 'Aveo', b'1');
INSERT INTO `modelo` VALUES (11, 4, 'Onix', b'1');
INSERT INTO `modelo` VALUES (12, 4, 'Tracker', b'1');
INSERT INTO `modelo` VALUES (13, 5, 'Fiesta', b'1');
INSERT INTO `modelo` VALUES (14, 5, 'Focus', b'1');
INSERT INTO `modelo` VALUES (15, 5, 'Ranger', b'1');
INSERT INTO `modelo` VALUES (16, 6, 'Jetta', b'1');
INSERT INTO `modelo` VALUES (17, 6, 'Golf', b'1');
INSERT INTO `modelo` VALUES (18, 6, 'Vento', b'1');
INSERT INTO `modelo` VALUES (19, 7, 'Mazda 2', b'1');
INSERT INTO `modelo` VALUES (20, 7, 'Mazda 3', b'1');
INSERT INTO `modelo` VALUES (21, 7, 'CX-5', b'1');
INSERT INTO `modelo` VALUES (22, 8, 'Rio', b'1');
INSERT INTO `modelo` VALUES (23, 8, 'Forte', b'1');
INSERT INTO `modelo` VALUES (24, 8, 'Sportage', b'1');
INSERT INTO `modelo` VALUES (25, 9, 'Accent', b'1');
INSERT INTO `modelo` VALUES (26, 9, 'Elantra', b'1');
INSERT INTO `modelo` VALUES (27, 9, 'Tucson', b'1');
INSERT INTO `modelo` VALUES (28, 10, 'Serie 3', b'1');
INSERT INTO `modelo` VALUES (29, 10, 'X3', b'1');
INSERT INTO `modelo` VALUES (30, 11, 'Clase C', b'1');
INSERT INTO `modelo` VALUES (31, 11, 'GLA', b'1');
INSERT INTO `modelo` VALUES (32, 12, 'A3', b'1');
INSERT INTO `modelo` VALUES (33, 12, 'Q5', b'1');
INSERT INTO `modelo` VALUES (34, 13, 'Swift', b'1');
INSERT INTO `modelo` VALUES (35, 13, 'Vitara', b'1');
INSERT INTO `modelo` VALUES (36, 14, 'FZ', b'1');
INSERT INTO `modelo` VALUES (37, 14, 'R15', b'1');
INSERT INTO `modelo` VALUES (38, 15, 'FT150', b'1');
INSERT INTO `modelo` VALUES (39, 15, 'DM200', b'1');

-- ----------------------------
-- Table structure for vehiculo
-- ----------------------------
DROP TABLE IF EXISTS `vehiculo`;
CREATE TABLE `vehiculo`  (
  `idVehiculo` int NOT NULL AUTO_INCREMENT,
  `idUsuario` int NOT NULL,
  `claveVehiculo` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `idModelo` int NOT NULL,
  `placa` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `color` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `anio` int NOT NULL,
  `descripcion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`idVehiculo`) USING BTREE,
  INDEX `idModelo`(`idModelo` ASC) USING BTREE,
  CONSTRAINT `vehiculo_ibfk_1` FOREIGN KEY (`idModelo`) REFERENCES `modelo` (`idModelo`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vehiculo
-- ----------------------------

-- ----------------------------
-- View structure for vehiculofullinfo
-- ----------------------------
DROP VIEW IF EXISTS `vehiculofullinfo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `vehiculofullinfo` AS select `v`.`idVehiculo` AS `idVehiculo`,`v`.`idUsuario` AS `idUsuario`,`v`.`claveVehiculo` AS `claveVehiculo`,`m1`.`idMarca` AS `idMarca`,`m1`.`marca` AS `marca`,`v`.`idModelo` AS `idModelo`,`m`.`modelo` AS `modelo`,`v`.`placa` AS `placa`,`v`.`color` AS `color`,`v`.`anio` AS `anio`,`v`.`descripcion` AS `descripcion` from ((`vehiculo` `v` join `modelo` `m` on((`m`.`idModelo` = `v`.`idModelo`))) join `marca` `m1` on((`m1`.`idMarca` = `m`.`idMarca`)));

SET FOREIGN_KEY_CHECKS = 1;
