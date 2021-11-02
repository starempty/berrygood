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
-- Table structure for table `comp`
--

DROP TABLE IF EXISTS `comp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comp` (
  `no` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comp`
--

LOCK TABLES `comp` WRITE;
/*!40000 ALTER TABLE `comp` DISABLE KEYS */;
INSERT INTO `comp` VALUES (1,'test@gmail.com','단컴퍼니','$2a$10$42/EuNtjEeVQtAGXuRf22.jWGGEr.mUbrQtfGqeoPqzOO/RWJMAQC','010-6644-1160','구미'),(2,'campus@naver.com','ssafy','$2a$10$e8ANjbsnFkVfImFrbU3wJedpHyTzUUFxV0ieUrfD/HJwLHfxuOl0K','010-9999-9999','campus'),(3,'sasung@naver.com','SASUNG','$2a$10$3c9ReIULGDL9gQE3fWWFyeM5JnvOmz22RxdlynHMZfX60y9jmw2zC','010-1111-1111','SASUNG'),(4,'green@naver.com','그린피스','$2a$10$F9RmV0y9DMvm2RHU4G8PNeWalUOyvyc4XXuRnKfxkRUJ8lWqInlyS','010-2222-2222','Green'),(5,'child@naver.com','어린이재단','$2a$10$kklr1N7FqDjHLlB3e1DkxOcz0H80P1GFsv3lK28.GV.jAoIHknNxC','010-1111-2222','CHILD'),(6,'animal@naver.com','동물보호단체','$2a$10$86oSWkgSfGv4/7LfvX8gXeHfJWUgTIUCTaJ9tIHE3oL.6UKoySSL.','010-3333-4444','ANIMAL'),(7,'dog@naver.com','유기견보호협회','$2a$10$0xSXZVZuIjjKfgQ8Dg07fez9uS2OGmYPbNgx8hLwqtC0/Wni6VahK','010-4444-5555','dogdog'),(8,'service@naver.com','종합사회복지센터','$2a$10$OQi.YCrBtxKnhw175ptsJO2Zypg9VwVIyOylZQj6wchu95BATLVTa','010-4455-5544','service'),(9,'comptest@test.test','TEST','$2a$10$j7IHmaUvGpz9wJx7K9Ip9utEixC56zA5FG2jwO7WcrVEW0sJYY/oO','010-0000-0000','test'),(10,'test1@gmail.com','단컴퍼니','$2a$10$lIJZFFx2DBihuLkfin6HDer2.pTUM9DqXU7FMVUEoi2d2kPq2lpTW','010-6644-1160','구미'),(12,'bbaee@gmail.com','베리굿재단','$2a$10$xEJn93NG1LND2IjhsITnPu9F2tsD5nZr9O83Fo1Hz5xv.SkLnYLdS','010-1324-5698','대구');
/*!40000 ALTER TABLE `comp` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-07 16:56:16
