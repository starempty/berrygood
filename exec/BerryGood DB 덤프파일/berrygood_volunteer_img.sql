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
-- Table structure for table `volunteer_img`
--

DROP TABLE IF EXISTS `volunteer_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `volunteer_img` (
  `no` bigint NOT NULL AUTO_INCREMENT,
  `img_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `volunteer_fk` bigint DEFAULT NULL,
  PRIMARY KEY (`no`),
  KEY `FK4u0snq8jxgekl28nl553yjq7c` (`volunteer_fk`),
  CONSTRAINT `FK4u0snq8jxgekl28nl553yjq7c` FOREIGN KEY (`volunteer_fk`) REFERENCES `volunteer` (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volunteer_img`
--

LOCK TABLES `volunteer_img` WRITE;
/*!40000 ALTER TABLE `volunteer_img` DISABLE KEYS */;
INSERT INTO `volunteer_img` VALUES (9,'images/volunteer/8/코로나도움.PNG',8),(12,'images/volunteer/10/액자.jpg',10),(13,'images/volunteer/9/자원봉사캠프 활동가 모집 안내문.png',9),(14,'images/volunteer/9/자원봉사캠프 활동가 활동 안내문.png',9),(15,'images/volunteer/11/소방관.jpg',11),(16,'images/volunteer/12/수요처지도.png',12),(17,'images/volunteer/13/거창지도.png',13),(18,'images/volunteer/14/심폐소생교육.PNG',14),(19,'images/volunteer/15/용봉천.jpg',15);
/*!40000 ALTER TABLE `volunteer_img` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-07 16:56:12
