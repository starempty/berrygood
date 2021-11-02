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
-- Table structure for table `donate_img`
--

DROP TABLE IF EXISTS `donate_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donate_img` (
  `no` bigint NOT NULL AUTO_INCREMENT,
  `img_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `donate_fk` bigint DEFAULT NULL,
  PRIMARY KEY (`no`),
  KEY `FK8046kyq3djdhv7255wrq9hvwf` (`donate_fk`),
  CONSTRAINT `FK8046kyq3djdhv7255wrq9hvwf` FOREIGN KEY (`donate_fk`) REFERENCES `donate` (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donate_img`
--

LOCK TABLES `donate_img` WRITE;
/*!40000 ALTER TABLE `donate_img` DISABLE KEYS */;
INSERT INTO `donate_img` VALUES (3,'images/donate/3/나무.png',3),(4,'images/donate/3/나무2.png',3),(5,'images/donate/4/그린피스1.jpg',4),(6,'images/donate/4/그린피스2.jpg',4),(7,'images/donate/5/폭력1.jpg',5),(8,'images/donate/5/폭력2.jpg',5),(9,'images/donate/6/수달.jpg',6),(10,'images/donate/7/곰.jpg',7),(11,'images/donate/8/유기견1.jpg',8),(12,'images/donate/8/유기견2.jpg',8),(13,'images/donate/9/골댕이.jpg',9),(14,'images/donate/10/택배.jpg',10);
/*!40000 ALTER TABLE `donate_img` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-07 16:56:09
