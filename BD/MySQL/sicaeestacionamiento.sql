/*
 Navicat Premium Dump SQL

 Source Server         : MYSQL
 Source Server Type    : MySQL
 Source Server Version : 80045 (8.0.45)
 Source Host           : localhost:3306
 Source Schema         : sicaeestacionamiento

 Target Server Type    : MySQL
 Target Server Version : 80045 (8.0.45)
 File Encoding         : 65001

 Date: 26/05/2026 12:36:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for espacioestacionamiento
-- ----------------------------
DROP TABLE IF EXISTS `espacioestacionamiento`;
CREATE TABLE `espacioestacionamiento`  (
  `idEspacio` int NOT NULL AUTO_INCREMENT,
  `claveEspacio` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tipo` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ocupado` bit(1) NOT NULL,
  `estatus` bit(1) NOT NULL,
  PRIMARY KEY (`idEspacio`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of espacioestacionamiento
-- ----------------------------
INSERT INTO `espacioestacionamiento` VALUES (1, 'A001', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (2, 'A002', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (3, 'A003', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (4, 'A004', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (5, 'A005', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (6, 'A006', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (7, 'A007', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (8, 'A008', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (9, 'A009', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (10, 'A010', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (11, 'A011', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (12, 'A012', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (13, 'A013', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (14, 'A014', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (15, 'A015', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (16, 'A016', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (17, 'A017', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (18, 'A018', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (19, 'A019', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (20, 'A020', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (21, 'A021', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (22, 'A022', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (23, 'A023', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (24, 'A024', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (25, 'A025', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (26, 'A026', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (27, 'A027', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (28, 'A028', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (29, 'A029', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (30, 'A030', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (31, 'A031', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (32, 'A032', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (33, 'A033', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (34, 'A034', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (35, 'A035', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (36, 'A036', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (37, 'A037', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (38, 'A038', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (39, 'A039', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (40, 'A040', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (41, 'A041', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (42, 'A042', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (43, 'A043', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (44, 'A044', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (45, 'A045', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (46, 'A046', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (47, 'A047', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (48, 'A048', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (49, 'A049', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (50, 'A050', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (51, 'A051', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (52, 'A052', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (53, 'A053', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (54, 'A054', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (55, 'A055', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (56, 'A056', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (57, 'A057', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (58, 'A058', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (59, 'A059', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (60, 'A060', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (61, 'A061', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (62, 'A062', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (63, 'A063', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (64, 'A064', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (65, 'A065', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (66, 'A066', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (67, 'A067', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (68, 'A068', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (69, 'A069', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (70, 'A070', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (71, 'A071', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (72, 'A072', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (73, 'A073', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (74, 'A074', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (75, 'A075', 'A', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (76, 'M001', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (77, 'M002', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (78, 'M003', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (79, 'M004', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (80, 'M005', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (81, 'M006', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (82, 'M007', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (83, 'M008', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (84, 'M009', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (85, 'M010', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (86, 'M011', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (87, 'M012', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (88, 'M013', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (89, 'M014', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (90, 'M015', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (91, 'M016', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (92, 'M017', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (93, 'M018', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (94, 'M019', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (95, 'M020', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (96, 'M021', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (97, 'M022', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (98, 'M023', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (99, 'M024', 'M', b'0', b'1');
INSERT INTO `espacioestacionamiento` VALUES (100, 'M025', 'M', b'0', b'1');

-- ----------------------------
-- Table structure for movimiento
-- ----------------------------
DROP TABLE IF EXISTS `movimiento`;
CREATE TABLE `movimiento`  (
  `idMovimiento` int NOT NULL AUTO_INCREMENT,
  `idVehiculo` int NOT NULL,
  `tiempoEntrada` timestamp NOT NULL,
  `tiempoSalida` timestamp NOT NULL,
  `minutosEstacionado` int NULL DEFAULT NULL,
  `horasCobradas` int NULL DEFAULT NULL,
  `costoTotal` decimal(10, 2) NULL DEFAULT NULL,
  `tarifaHora` decimal(10, 2) NOT NULL,
  `tiempoCreacion` timestamp NULL DEFAULT NULL,
  `tiempoActualizacion` timestamp NULL DEFAULT NULL,
  `idEspacio` int NOT NULL,
  PRIMARY KEY (`idMovimiento`) USING BTREE,
  INDEX `idEspacio`(`idEspacio` ASC) USING BTREE,
  CONSTRAINT `movimiento_ibfk_1` FOREIGN KEY (`idEspacio`) REFERENCES `espacioestacionamiento` (`idEspacio`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movimiento
-- ----------------------------

-- ----------------------------
-- View structure for movimientofullinfo
-- ----------------------------
DROP VIEW IF EXISTS `movimientofullinfo`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `movimientofullinfo` AS select `m`.`idMovimiento` AS `idMovimiento`,`m`.`idVehiculo` AS `idVehiculo`,`m`.`tiempoEntrada` AS `tiempoEntrada`,`m`.`tiempoSalida` AS `tiempoSalida`,`m`.`minutosEstacionado` AS `minutosEstacionado`,`m`.`horasCobradas` AS `horasCobradas`,`m`.`costoTotal` AS `costoTotal`,`m`.`tarifaHora` AS `tarifaHora`,`m`.`tiempoCreacion` AS `tiempoCreacion`,`m`.`tiempoActualizacion` AS `tiempoActualizacion`,`m`.`idEspacio` AS `idEspacio`,`ee`.`claveEspacio` AS `claveEspacio`,`ee`.`tipo` AS `tipoEspacio` from (`movimiento` `m` join `espacioestacionamiento` `ee` on((0 <> `ee`.`idEspacio`)));

SET FOREIGN_KEY_CHECKS = 1;
