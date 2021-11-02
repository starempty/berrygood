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
-- Table structure for table `donate_participant`
--

DROP TABLE IF EXISTS `donate_participant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donate_participant` (
  `no` bigint NOT NULL AUTO_INCREMENT,
  `amount` bigint DEFAULT NULL,
  `join_date` datetime(6) DEFAULT NULL,
  `message` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `donate_no` bigint DEFAULT NULL,
  `user_no` bigint DEFAULT NULL,
  PRIMARY KEY (`no`),
  KEY `FK8t00vktdlnh4tiej09hmjip0g` (`donate_no`),
  KEY `FK3u85h3r0xwop82grm46i80enm` (`user_no`),
  CONSTRAINT `FK3u85h3r0xwop82grm46i80enm` FOREIGN KEY (`user_no`) REFERENCES `user` (`no`),
  CONSTRAINT `FK8t00vktdlnh4tiej09hmjip0g` FOREIGN KEY (`donate_no`) REFERENCES `donate` (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donate_participant`
--

LOCK TABLES `donate_participant` WRITE;
/*!40000 ALTER TABLE `donate_participant` DISABLE KEYS */;
INSERT INTO `donate_participant` VALUES (5,1,'2021-10-07 03:04:45.780000','응원합니다!',9,4),(6,1,'2021-10-07 03:18:27.448000','응원합니다.',9,4),(7,1,'2021-10-07 03:23:27.472000','화이팅!',9,4),(8,1,'2021-10-07 03:27:18.688000','잘 써주세요 !!',9,4),(9,1,'2021-10-07 03:39:35.809000','유기견을 부탁합니다.',9,4),(10,1,'2021-10-07 03:59:26.052000','멍멍이들아 힘내!!',8,10),(11,1,'2021-10-07 04:10:55.811000','댕댕',9,3),(12,1,'2021-10-07 04:14:14.088000','수달이 화이팅!',6,10),(13,10,'2021-10-07 04:14:14.363000','항상 감사합니다~',9,4),(14,1,'2021-10-07 04:19:19.645000','수달아 힘내야해!',6,10),(15,1,'2021-10-07 13:42:11.728000','dd',9,3),(16,1,'2021-10-07 13:44:15.536000','dd',9,3),(17,1,'2021-10-07 16:25:33.729000','화이팅',9,17);
/*!40000 ALTER TABLE `donate_participant` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-07 16:56:13
