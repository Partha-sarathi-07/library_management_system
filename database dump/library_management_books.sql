CREATE DATABASE  IF NOT EXISTS `library_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `library_management`;
-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: library_management
-- ------------------------------------------------------
-- Server version	8.0.39-0ubuntu0.22.04.1

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
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `book_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL,
  `genre` varchar(20) DEFAULT NULL,
  `available_copies` int DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (2,'Song of Ice and Fire','George R R Martin','fiction',4),(3,'Harry Potter','J K Rowling','fantasy',3),(4,'To Kill a Mockingbird','Harper Lee','classic',7),(5,'The Great Gatsby','F. Scott Fitzgerald','classic',7),(6,'1984','George Orwell','dystopian',6),(7,'The Hobbit','J.R.R. Tolkien','fantasy',9),(8,'Pride and Prejudice','Jane Austen','romance',5),(9,'The Da Vinci Code','Dan Brown','thriller',4),(10,'The Catcher in the Rye','J.D. Salinger','fiction',7),(11,'The Road','Cormac McCarthy','fiction',5),(12,'Life of Pi','Yann Martel','fiction',8),(13,'The Hobbit','J.R.R. Tolkien','fantasy',4),(14,'The Name of the Wind','Patrick Rothfuss','fantasy',3),(15,'Percy Jackson & The Olympians','Rick Riordan','fantasy',6),(16,'Pride and Prejudice','Jane Austen','classic',5),(17,'1984','George Orwell','classic',6),(18,'Jane Eyre','Charlotte Bronte','classic',7),(19,'The Girl with the Dragon Tattoo','Stieg Larsson','thriller',5),(20,'Gone Girl','Gillian Flynn','thriller',4),(21,'The Silence of the Lambs','Thomas Harris','thriller',5),(22,'The Hunger Games','Suzanne Collins','dystopian',8),(23,'Brave New World','Aldous Huxley','dystopian',5),(24,'Fahrenheit 451','Ray Bradbury','dystopian',5),(25,'The Notebook','Nicholas Sparks','romance',3),(26,'Me Before You','Jojo Moyes','romance',4),(27,'Outlander','Diana Gabaldon','romance',7),(28,'Ponniyin Selvan','Kalki','fiction',3),(29,'Sivagamiyin Sabatham','Kalki','fiction',4),(31,'Severence','Ling Ma','dystopian',7);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-21 22:46:47
