-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: barberia
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `barbero`
--

DROP TABLE IF EXISTS `barbero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `barbero` (
  `ID_BARBERO` int NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `EDAD` int DEFAULT NULL,
  PRIMARY KEY (`ID_BARBERO`),
  UNIQUE KEY `ID_BARBERO` (`ID_BARBERO`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `barbero`
--

LOCK TABLES `barbero` WRITE;
/*!40000 ALTER TABLE `barbero` DISABLE KEYS */;
INSERT INTO `barbero` VALUES (1,'DON RAMON',67),(2,'DON  BARRIGA',65),(3,'GODINES ',40);
/*!40000 ALTER TABLE `barbero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `ID_CLIENTE` int NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `EDAD` int DEFAULT NULL,
  PRIMARY KEY (`ID_CLIENTE`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'DON PICHON',67);
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_log`
--

DROP TABLE IF EXISTS `login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login_log` (
  `ID_LOG` int NOT NULL AUTO_INCREMENT,
  `ID_USER` int DEFAULT NULL,
  `N_USER` varchar(50) DEFAULT NULL,
  `FECHA_HORA` datetime DEFAULT CURRENT_TIMESTAMP,
  `EVENTO` enum('LOGIN','FALLIDO','LOGOUT') DEFAULT NULL,
  PRIMARY KEY (`ID_LOG`),
  UNIQUE KEY `ID_LOG` (`ID_LOG`),
  KEY `ID_USER` (`ID_USER`),
  CONSTRAINT `login_log_ibfk_1` FOREIGN KEY (`ID_USER`) REFERENCES `users` (`ID_USER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_log`
--

LOCK TABLES `login_log` WRITE;
/*!40000 ALTER TABLE `login_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagos`
--

DROP TABLE IF EXISTS `pagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pagos` (
  `ID_PAGO` int NOT NULL AUTO_INCREMENT,
  `PAGO` int DEFAULT NULL,
  `ID_BARBERO` int DEFAULT NULL,
  `ID_SERVICIO` int DEFAULT NULL,
  PRIMARY KEY (`ID_PAGO`),
  KEY `ID_BARBERO` (`ID_BARBERO`),
  KEY `ID_SERVICIO` (`ID_SERVICIO`),
  CONSTRAINT `pagos_ibfk_1` FOREIGN KEY (`ID_BARBERO`) REFERENCES `barbero` (`ID_BARBERO`),
  CONSTRAINT `pagos_ibfk_2` FOREIGN KEY (`ID_SERVICIO`) REFERENCES `servicio` (`ID_SERVICIO`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos`
--

LOCK TABLES `pagos` WRITE;
/*!40000 ALTER TABLE `pagos` DISABLE KEYS */;
INSERT INTO `pagos` VALUES (1,8000,1,1);
/*!40000 ALTER TABLE `pagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicio`
--

DROP TABLE IF EXISTS `servicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicio` (
  `ID_SERVICIO` int NOT NULL AUTO_INCREMENT,
  `TURNO` int DEFAULT NULL,
  `PRECIO` int DEFAULT NULL,
  `FECHA` date DEFAULT NULL,
  `ID_USER` int DEFAULT NULL,
  `ID_CLIENTE` int DEFAULT NULL,
  `ID_BARBERO` int DEFAULT NULL,
  PRIMARY KEY (`ID_SERVICIO`),
  KEY `ID_USER` (`ID_USER`),
  KEY `ID_CLIENTE` (`ID_CLIENTE`),
  KEY `ID_BARBERO` (`ID_BARBERO`),
  CONSTRAINT `servicio_ibfk_1` FOREIGN KEY (`ID_USER`) REFERENCES `users` (`ID_USER`),
  CONSTRAINT `servicio_ibfk_2` FOREIGN KEY (`ID_CLIENTE`) REFERENCES `clientes` (`ID_CLIENTE`),
  CONSTRAINT `servicio_ibfk_3` FOREIGN KEY (`ID_BARBERO`) REFERENCES `barbero` (`ID_BARBERO`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicio`
--

LOCK TABLES `servicio` WRITE;
/*!40000 ALTER TABLE `servicio` DISABLE KEYS */;
INSERT INTO `servicio` VALUES (1,1,20000,'2026-11-01',1,1,1);
/*!40000 ALTER TABLE `servicio` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `inicios_sesion` AFTER INSERT ON `servicio` FOR EACH ROW begin
INSERT INTO PAGOS(PAGO, ID_BARBERO, ID_SERVICIO)
VALUES (ROUND(NEW.PRECIO * 0.40), NEW.ID_BARBERO, NEW.ID_SERVICIO);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `ID_USER` int NOT NULL AUTO_INCREMENT,
  `N_USER` varchar(50) DEFAULT NULL,
  `PASS` varchar(50) DEFAULT NULL,
  `ID_ROLE` int DEFAULT NULL,
  PRIMARY KEY (`ID_USER`),
  UNIQUE KEY `ID_USER` (`ID_USER`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'CREGUILLO','1234',1),(2,'MCAICEDO','1234',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `verbarbero`
--

DROP TABLE IF EXISTS `verbarbero`;
/*!50001 DROP VIEW IF EXISTS `verbarbero`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `verbarbero` AS SELECT 
 1 AS `ID`,
 1 AS `NOMBRE`,
 1 AS `EDAD`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `verclientes`
--

DROP TABLE IF EXISTS `verclientes`;
/*!50001 DROP VIEW IF EXISTS `verclientes`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `verclientes` AS SELECT 
 1 AS `ID`,
 1 AS `NOMBRE`,
 1 AS `EDAD`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vertabla`
--

DROP TABLE IF EXISTS `vertabla`;
/*!50001 DROP VIEW IF EXISTS `vertabla`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vertabla` AS SELECT 
 1 AS `ID`,
 1 AS `NOMBRE`,
 1 AS `TURNO`,
 1 AS `BARBERO`,
 1 AS `FECHAS`,
 1 AS `PRECIO`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `verbarbero`
--

/*!50001 DROP VIEW IF EXISTS `verbarbero`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `verbarbero` AS select `b`.`ID_BARBERO` AS `ID`,`b`.`NOMBRE` AS `NOMBRE`,`b`.`EDAD` AS `EDAD` from `barbero` `b` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `verclientes`
--

/*!50001 DROP VIEW IF EXISTS `verclientes`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `verclientes` AS select `c`.`ID_CLIENTE` AS `ID`,`c`.`NOMBRE` AS `NOMBRE`,`c`.`EDAD` AS `EDAD` from `clientes` `c` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vertabla`
--

/*!50001 DROP VIEW IF EXISTS `vertabla`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vertabla` AS select `c`.`ID_CLIENTE` AS `ID`,`c`.`NOMBRE` AS `NOMBRE`,`s`.`TURNO` AS `TURNO`,`b`.`NOMBRE` AS `BARBERO`,`s`.`FECHA` AS `FECHAS`,`s`.`PRECIO` AS `PRECIO` from ((`clientes` `c` join `servicio` `s` on((`c`.`ID_CLIENTE` = `s`.`ID_CLIENTE`))) join `barbero` `b` on((`s`.`ID_BARBERO` = `b`.`ID_BARBERO`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-01 17:36:05
