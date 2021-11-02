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
-- Table structure for table `comp_wallet`
--

DROP TABLE IF EXISTS `comp_wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comp_wallet` (
  `no` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `balance` int NOT NULL,
  `berry` int NOT NULL,
  `owner_id` bigint DEFAULT NULL,
  PRIMARY KEY (`no`),
  KEY `FKpa0kjco6on6stk3huxab0cjy3` (`owner_id`),
  CONSTRAINT `FKpa0kjco6on6stk3huxab0cjy3` FOREIGN KEY (`owner_id`) REFERENCES `comp` (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comp_wallet`
--

LOCK TABLES `comp_wallet` WRITE;
/*!40000 ALTER TABLE `comp_wallet` DISABLE KEYS */;
INSERT INTO `comp_wallet` VALUES (4,'0x45fb68a495d35daE03252883D0927850Cb919fc8',16,9,7),(5,'0xd310C0d58F34E3db37f93640f10B4394954c6Dd9',1,4,6),(6,'0x276B2DE64074b72A12028e0e633DA333A10Fa4FE',0,0,5),(7,'0x968D43EFFE5d6Bf258e8A279BC49C89f65eaa72d',0,0,4),(8,'0xedBbD0b73Be50650d4199F190aEC0a7D72a3f09A',0,0,1),(9,'0x5C53700C1B4a875eB55257C163E7715caF886BeC',0,0,8);
/*!40000 ALTER TABLE `comp_wallet` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-07 16:56:10
