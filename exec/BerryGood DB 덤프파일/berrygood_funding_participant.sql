-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: j5d101.p.ssafy.io    Database: berrygood
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `funding_participant`
--

DROP TABLE IF EXISTS `funding_participant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funding_participant` (
  `no` bigint NOT NULL AUTO_INCREMENT,
  `amount` bigint DEFAULT NULL,
  `join_date` datetime(6) DEFAULT NULL,
  `funding_no` bigint DEFAULT NULL,
  `user_no` bigint DEFAULT NULL,
  PRIMARY KEY (`no`),
  KEY `FKqx3h5kwmqcqc0gj5tukeowkhx` (`funding_no`),
  KEY `FKp810nmbqdrgta600jkam8fu3w` (`user_no`),
  CONSTRAINT `FKp810nmbqdrgta600jkam8fu3w` FOREIGN KEY (`user_no`) REFERENCES `user` (`no`),
  CONSTRAINT `FKqx3h5kwmqcqc0gj5tukeowkhx` FOREIGN KEY (`funding_no`) REFERENCES `funding` (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funding_participant`
--

LOCK TABLES `funding_participant` WRITE;
/*!40000 ALTER TABLE `funding_participant` DISABLE KEYS */;
INSERT INTO `funding_participant` VALUES (3,1,'2021-10-07 16:09:46.434000',9,3),(4,1,'2021-10-07 16:13:39.231000',9,10),(5,2,'2021-10-07 16:17:50.545000',9,13);
/*!40000 ALTER TABLE `funding_participant` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-07 16:56:15
