CREATE DATABASE  IF NOT EXISTS `museo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `museo`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: museo
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `fotografos`
--

DROP TABLE IF EXISTS `fotografos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fotografos` (
  `fotografoId` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `galardonado` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`fotografoId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fotografos`
--

LOCK TABLES `fotografos` WRITE;
/*!40000 ALTER TABLE `fotografos` DISABLE KEYS */;
INSERT INTO `fotografos` VALUES (1,'luffy',1),(2,'zoro',1),(3,'nami',0),(4,'usopp',1),(5,'brook',0);
/*!40000 ALTER TABLE `fotografos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fotos`
--

DROP TABLE IF EXISTS `fotos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fotos` (
  `fotosId` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(50) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `archivo` varchar(50) DEFAULT NULL,
  `visitas` int DEFAULT NULL,
  `fotografoId` int DEFAULT NULL,
  PRIMARY KEY (`fotosId`),
  KEY `fotografoId` (`fotografoId`),
  CONSTRAINT `fotos_ibfk_1` FOREIGN KEY (`fotografoId`) REFERENCES `fotografos` (`fotografoId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fotos`
--

LOCK TABLES `fotos` WRITE;
/*!40000 ALTER TABLE `fotos` DISABLE KEYS */;
INSERT INTO `fotos` VALUES (1,'cartelLuffy','2000-05-05','cartelLuffy.jpg',300000007,1),(2,'cartelZoro','2000-04-04','cartelZoro.jpg',120000003,2),(3,'cartelNami','2000-03-03','cartelNami.jpg',0,3),(4,'cartelSogeking','2000-02-02','cartelLuffy.jpg',30000001,4),(5,'cartelBrook','2000-01-01','cartelBrook.jpg',33000000,5),(6,'cartelLuffy2','2022-09-22','cartelLuffy2.jpg',1500000002,1),(7,'cartelZoro2','2022-10-15','cartelZoro2.jpg',320000000,2),(8,'cartelNami2','2022-11-13','cartelNami2.jpg',0,3),(9,'cartelSogeking2','2022-10-25','cartelSogeking2.jpg',200000001,4),(10,'cartelBrook2','2022-10-16','cartelBrook2.jpg',83000000,5);
/*!40000 ALTER TABLE `fotos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-10 20:25:16
