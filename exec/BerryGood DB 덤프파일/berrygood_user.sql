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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `no` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `birthday` datetime(6) DEFAULT NULL,
  `gender` char(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nickname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3,'sobin@naver.com','김소빈','$2a$10$.naxNfsAT3kMYK7w6lW4X.AWlHDdrVeh/OTWswCbnQwEkRnR.3.qC','010-1010-1010','뮤','2014-08-07 00:00:00.000000','f','밤고구마'),(4,'dansh87@gmail.com','단석환','$2a$10$.rkh.vWn8R8lKwdcJK6T.eJz2DXCGjyFTm4jFBM8QxIKdUCJzJiCS','010-6644-1160','구미','1995-04-04 00:00:00.000000','m','단컴퍼니사장'),(5,'ssafy@naver.com','ssafy','$2a$10$YstxsdY59ztAywyzgF8rUO7./fs5hYfOaiRFU179EuTMVoFf7TTBG','010-0000-0000','ssafy','2015-06-06 00:00:00.000000','m','ssafy'),(6,'test@gmail.com','테스트','$2a$10$/A4rmFrFho7olFxIKt6IBuZCG18Y2XD1kwob.KxOm2vmpgqSJxBbm','010-6644-1160','구미','1995-04-04 00:00:00.000000','m','단컴퍼니부장'),(7,'person1@naver.com','강싸피','$2a$10$/RluLp9O5/CKaN0NBjlP5.so2YBb50gCVLnYNI1PTBxeIigZHnZTq','010-9876-5432','강싸아피','2016-07-07 00:00:00.000000','m','강싸아피'),(8,'test@test.test','TEST','$2a$10$ZE6uPC/wq.h5njQUMcbCHOm9flnY1ViyMgyqITJbSfzR7IDA7ExL.','010-1234-5678','Test','2021-10-16 00:00:00.000000','m','TEST'),(9,'user1@naver.com','나유저','$2a$10$8o0a8tvrfL1RMAzSkDlj8uCne9PMeDCVq0kysFnSFqjLYH8kl0K7e','010-5494-1511','뮤','2016-07-07 00:00:00.000000','m','나유'),(10,'bbaee@naver.com','황배근','$2a$10$FC04542e6CpMuNjxlRTRwubvcPr7yi5LOVj28RtNGKhitk4Bpstqy','010-3190-3288','대구','1993-08-21 00:00:00.000000','m','bbaee'),(11,'person2@naver.com','최배달','$2a$10$yfyPspf8MW3sUOgQ3JWXDu3kZdjp1RiCqHVVaCati2Wo47QZaLMo.','010-8282-8282','배달의배달','2015-10-27 00:00:00.000000','m','퀵딜리버리'),(13,'person3@naver.com','김요기','$2a$10$mgNAOQsBXZrn7SwAg8GHcuoNy3/.EcF2QTtAMxc2zDtFeTLyiHt42','010-1324-6958','서울','2002-07-08 00:00:00.000000','f','요기요'),(14,'person5@naver.com','한여름','$2a$10$JoE2P.SrPcClVA.Bq79L8uCJy7eEgG4QOJbsUstXYmvjc8fNrIyKi','010-3214-9568','거창','1990-05-03 00:00:00.000000','f','여름아부탁해'),(15,'person4@naver.com','추사랑','$2a$10$bs8mcIAtkhp1Eg4Hn/yDWePSbiofevccehX4fuIb5kWpPnl63y13y','010-4545-6565','사랑','2007-11-02 00:00:00.000000','f','사랑이'),(16,'person6@naver.com','이지금','$2a$10$LpxNhfRXTEE6aBvy80k4/uiPcSg.K3VKAUF3/tB7PJH1EDqnwVOfu','010-3125-9565','서울','1993-04-03 00:00:00.000000','f','지금이'),(17,'snow@naver.com','설레임','$2a$10$DyQfek7gO1.xs8jrNanXVek.z.peJmoTonLGNiD1JaGozRGA5xxHe','010-8888-7777','설렘','2015-06-03 00:00:00.000000','m','설레임'),(18,'test123@naver.com','test123!','$2a$10$jeZEKoblMoTN.CY6GJ4uIu.PJCchExLHzhU1A.uXvotTZfhOZ2qM.','010-0000-0000','-','2015-07-08 00:00:00.000000','m','test123!'),(19,'test2@gmail.com','테스트','$2a$10$bWnOV2HB5nQM8b8wClObyeRO501eBZ3uQp8YooDL746aTlPJu6kwG','010-6644-1160','구미','1995-04-04 00:00:00.000000','m','단컴퍼니부사장');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-07 16:56:18
