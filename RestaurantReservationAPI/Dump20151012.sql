CREATE DATABASE  IF NOT EXISTS `restaurant_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `restaurant_db`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: restaurant_db
-- ------------------------------------------------------
-- Server version	5.6.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` decimal(13,2) NOT NULL,
  `description` varchar(500) NOT NULL,
  `HotFlag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'Cripsy Golden Roll',4.75,'Traditional fried golden roll filled with vegetables and soy protein',1),(2,'Spring Roll',5.95,'FResh rolls filled with steamed jicama, tofu, carrot, cilantro, lettuce and noodle wrapped in rice paper served with peanut sauce',0),(3,'Drumstick',6.95,'Crispy soy protein served with sweet and sour sauce',0),(4,'Happy Tamrind',6.95,'Slices soy protein cooked in sweet and sour tamarind sauce served with rice chip',0);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `owner`
--

DROP TABLE IF EXISTS `owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `owner` (
  `email` varchar(255) NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `owner`
--

LOCK TABLES `owner` WRITE;
/*!40000 ALTER TABLE `owner` DISABLE KEYS */;
INSERT INTO `owner` VALUES ('egen@solutions.com','d57587b0f5bbb0c3fe9d8cb16e97b0fe');
/*!40000 ALTER TABLE `owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reservation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CustomerFname` varchar(45) NOT NULL,
  `CUSTOMERLNAME` varchar(45) DEFAULT NULL,
  `PHONE` varchar(10) NOT NULL,
  `EMAIL` varchar(50) NOT NULL,
  `RESERVATIONDATE` date DEFAULT NULL,
  `RESERVATIONTIME` time NOT NULL,
  `PARTYSIZE` int(11) NOT NULL,
  `TABLENR` varchar(20) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (1,'Shweta','Thakare','3179093357','thakare.shweta@gmail.com','2014-02-02','09:00:00',2,'1','Booked'),(4,'Harry ','Potter','1111111111','hary.potter@gmail.com','2014-10-10','17:00:00',4,'3','booked'),(5,'Daphney','King','2222222222','hary.potter@gmail.com','2014-10-10','17:00:00',4,'4','booked'),(6,'Jason','Smith','2222222223','hary.potter@gmail.com','2015-10-10','17:00:00',4,'3','booked'),(7,'Nilesh','Ghadge','2222222224','thakare.shweta@gmail.com','2014-02-02','09:00:00',2,'1','booked'),(8,'Swati','Ghadge','2222222232','thakare.shweta@gmail.com','2014-02-02','09:00:00',2,'1','booked'),(9,'Chandar','Ghadge','3179093358','cr_ghadge@gmail.com','2014-02-02','09:00:00',2,'1','booked'),(10,'pushpa','Ghadge','3179093356','thakare.shweta@gmail.com','2014-02-02','09:00:00',2,'2','booked'),(11,'pushpa','Ghadge','3179093356','thakare.shweta@gmail.com','2014-02-02','09:00:00',2,'3','booked'),(12,'pushpa','Ghadge','3179093356','thakare.shweta@gmail.com','2014-02-02','09:00:00',2,'4','booked'),(13,'deepa','Ghadge','2323232323','thakare.shweta@gmail.com','2014-02-02','08:00:00',2,'1','booked'),(14,'deepa','Ghadge','2323232323','thakare.shweta@gmail.com','2014-02-02','11:00:00',2,'1','booked');
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurantdetails`
--

DROP TABLE IF EXISTS `restaurantdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `restaurantdetails` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) NOT NULL,
  `CONTACT` varchar(10) NOT NULL,
  `Address1` varchar(45) NOT NULL,
  `CITY` varchar(45) NOT NULL,
  `State` varchar(45) NOT NULL,
  `Country` varchar(45) NOT NULL,
  `AUTOASSIGN` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurantdetails`
--

LOCK TABLES `restaurantdetails` WRITE;
/*!40000 ALTER TABLE `restaurantdetails` DISABLE KEYS */;
INSERT INTO `restaurantdetails` VALUES (1,'Holmes','333999000','221B Baker Street','London','UK','UK',1);
/*!40000 ALTER TABLE `restaurantdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tabledetails`
--

DROP TABLE IF EXISTS `tabledetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tabledetails` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TABLESIZE` int(11) NOT NULL,
  `TABLENAME` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tabledetails`
--

LOCK TABLES `tabledetails` WRITE;
/*!40000 ALTER TABLE `tabledetails` DISABLE KEYS */;
INSERT INTO `tabledetails` VALUES (1,2,'T201'),(2,2,'T202'),(3,4,'T401'),(4,4,'T402');
/*!40000 ALTER TABLE `tabledetails` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-12  9:44:04
