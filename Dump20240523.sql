CREATE DATABASE  IF NOT EXISTS `project_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `project_db`;
-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: project_db
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gathering_area`
--

DROP TABLE IF EXISTS `gathering_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `gathering_area` (
  `gathering_area_id` int(11) NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `people_quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`gathering_area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gathering_area`
--

LOCK TABLES `gathering_area` WRITE;
/*!40000 ALTER TABLE `gathering_area` DISABLE KEYS */;
INSERT INTO `gathering_area` VALUES (1,'31.531442, 34.476093',210),(2,'31.251388, 34.264050',100),(3,'31.456686, 34.434408',290);
/*!40000 ALTER TABLE `gathering_area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `item` (
  `item_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `exp_date` date DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `supplier_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `fk_item_supplier` (`supplier_id`),
  CONSTRAINT `fk_item_supplier` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'M4A1 Rifle',4,NULL,3000,1,1),(2,'Negev LMG',4,NULL,7000,1,1),(3,'Glock 17 Pistol',1,NULL,1000,1,1),(4,'100 Rounds of 5.56 NATO',1,NULL,500,2,1),(5,'50 Rounds of 7.62x39mm',1,NULL,400,2,1),(6,'20 Rounds of 9mm',1,NULL,450,2,1),(7,'White Rice',1,'2025-07-10',10,3,2),(8,'Canned Beans',1,'2024-12-31',5,3,2),(9,'Bottled Water',2,'2024-12-31',2,3,2),(10,'10 Toilet Paper Rolls',2,NULL,20,4,3),(11,'5 Bars of Soap',1,NULL,10,4,3),(12,'1 Bottle of Disinfectant',1,NULL,5,4,3),(13,'Large Type B Uniform',1,NULL,20,5,4),(14,'Medium Type A Uniform',1,NULL,18,5,4),(15,'Small Type C Uniform',1,NULL,22,5,4);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order` (
  `order_id` int(11) NOT NULL,
  `order_date` date DEFAULT NULL,
  `gathering_area_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `gathering_area_id` (`gathering_area_id`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`gathering_area_id`) REFERENCES `gathering_area` (`gathering_area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,'2024-05-21',1),(2,'2024-05-21',1),(3,'2024-05-21',1),(4,'2024-05-21',1),(5,'2024-05-21',1),(6,'2024-05-21',1),(7,'2024-05-21',1),(8,'2024-05-21',1),(9,'2024-05-21',1),(10,'2024-05-21',1),(11,'2024-05-21',1),(12,'2024-05-21',1),(13,'2024-05-21',1),(14,'2024-05-21',1),(15,'2024-05-21',1),(16,'2024-05-21',1),(17,'2024-05-21',1),(18,'2024-05-21',1),(19,'2024-05-21',1),(20,'2024-05-21',1),(21,'2024-05-22',1),(22,'2024-05-22',1),(23,'2024-05-22',1),(24,'2024-05-22',1),(25,'2024-05-22',1),(26,'2024-05-22',1),(27,'2024-05-23',1),(28,'2024-05-23',1),(29,'2024-05-23',1),(30,'2024-05-23',1);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `order_BEFORE_INSERT` BEFORE INSERT ON `order` FOR EACH ROW BEGIN
    DECLARE max_order_id INT;

    -- Find the current maximum order_id
    SELECT COALESCE(MAX(order_id), 0) INTO max_order_id FROM `order`;

    -- Set the new order_id to be max_order_id + 1
    SET NEW.order_id = max_order_id + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `order_item_relation`
--

DROP TABLE IF EXISTS `order_item_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_item_relation` (
  `order_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`,`item_id`),
  KEY `item_id` (`item_id`),
  CONSTRAINT `order_item_relation_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`),
  CONSTRAINT `order_item_relation_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item_relation`
--

LOCK TABLES `order_item_relation` WRITE;
/*!40000 ALTER TABLE `order_item_relation` DISABLE KEYS */;
INSERT INTO `order_item_relation` VALUES (1,1,500),(1,2,500),(1,3,500),(1,4,500),(1,5,500),(1,6,500),(1,7,500),(1,8,500),(1,9,500),(3,1,500),(3,2,500),(3,3,500),(3,4,500),(3,5,500),(3,6,500),(3,7,500),(3,8,500),(3,9,500),(4,1,500),(4,2,500),(4,3,500),(4,4,500),(4,5,500),(4,6,500),(4,7,500),(4,8,500),(4,9,500),(5,1,500),(5,2,500),(5,3,500),(5,4,500),(5,5,500),(5,6,500),(5,7,500),(5,8,500),(5,9,500),(6,1,500),(6,2,500),(6,3,500),(6,4,500),(6,5,500),(6,6,500),(6,7,500),(6,8,500),(6,9,500),(7,1,500),(7,2,500),(7,3,500),(7,4,500),(7,5,500),(7,6,500),(7,7,500),(7,8,500),(7,9,500),(8,1,500),(8,2,500),(8,3,500),(8,4,500),(8,5,500),(8,6,500),(8,7,500),(8,8,500),(8,9,500),(9,1,500),(9,2,500),(9,3,500),(9,4,500),(9,5,500),(9,6,500),(9,7,500),(9,8,500),(9,9,500),(10,1,500),(10,2,500),(10,3,500),(10,4,500),(10,5,500),(10,6,500),(10,7,500),(10,8,500),(10,9,500),(11,1,500),(11,2,500),(11,3,500),(11,4,500),(11,5,500),(11,6,500),(11,7,500),(11,8,500),(11,9,500),(12,1,500),(12,2,500),(12,3,500),(12,4,500),(12,5,500),(12,6,500),(12,7,500),(12,8,500),(12,9,500),(13,1,500),(13,2,500),(13,3,500),(13,4,500),(13,5,500),(13,6,500),(13,7,500),(13,8,500),(13,9,500),(14,1,500),(14,2,500),(14,3,500),(14,4,500),(14,5,500),(14,6,500),(14,7,500),(14,8,500),(14,9,500),(15,1,500),(15,2,500),(15,3,500),(15,4,500),(15,5,500),(15,6,500),(15,7,500),(15,8,500),(15,9,500),(16,1,500),(16,2,500),(16,3,500),(16,4,500),(16,5,500),(16,6,500),(16,7,500),(16,8,500),(16,9,500),(17,1,500),(17,2,500),(17,3,500),(17,4,500),(17,5,500),(17,6,500),(17,7,500),(17,8,500),(17,9,500),(18,1,500),(18,2,500),(18,3,500),(18,4,500),(18,5,500),(18,6,500),(18,7,500),(18,8,500),(18,9,500),(19,1,500),(19,2,500),(19,3,500),(19,4,500),(19,5,500),(19,6,500),(19,7,500),(19,8,500),(19,9,500),(20,1,500),(20,2,500),(20,3,500),(20,4,500),(20,5,500),(20,6,500),(20,7,500),(20,8,500),(20,9,500),(21,1,500),(21,2,500),(21,3,500),(21,4,500),(21,5,500),(21,6,500),(21,7,500),(21,8,500),(21,9,500),(22,1,500),(22,2,500),(22,3,500),(22,4,500),(22,5,500),(22,6,500),(22,7,500),(22,8,500),(22,9,500),(23,1,500),(23,2,500),(23,3,500),(23,4,500),(23,5,500),(23,6,500),(23,7,500),(23,8,500),(23,9,500),(24,1,500),(24,2,500),(24,3,500),(24,4,500),(24,5,500),(24,6,500),(24,7,500),(24,8,500),(24,9,500),(25,1,500),(25,2,500),(25,3,500),(25,4,500),(25,5,500),(25,6,500),(25,7,500),(25,8,500),(25,9,500),(26,1,500),(26,2,500),(26,3,500),(26,4,500),(26,5,500),(26,6,500),(26,7,500),(26,8,500),(26,9,500),(27,1,500),(27,2,500),(27,3,500),(27,4,500),(27,5,500),(27,6,500),(27,7,500),(27,8,500),(27,9,500),(28,1,500),(28,2,500),(28,3,500),(28,4,500),(28,5,500),(28,6,500),(28,7,500),(28,8,500),(28,9,500),(29,1,500),(29,2,500),(29,3,500),(29,4,500),(29,5,500),(29,6,500),(29,7,500),(29,8,500),(29,9,500),(30,1,500),(30,2,500),(30,3,500),(30,4,500),(30,5,500),(30,6,500),(30,7,500),(30,8,500),(30,9,500);
/*!40000 ALTER TABLE `order_item_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storage_item_relation`
--

DROP TABLE IF EXISTS `storage_item_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `storage_item_relation` (
  `storage_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`storage_id`,`item_id`),
  KEY `item_id` (`item_id`),
  CONSTRAINT `storage_item_relation_ibfk_1` FOREIGN KEY (`storage_id`) REFERENCES `storage_unit` (`storage_id`),
  CONSTRAINT `storage_item_relation_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage_item_relation`
--

LOCK TABLES `storage_item_relation` WRITE;
/*!40000 ALTER TABLE `storage_item_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `storage_item_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storage_unit`
--

DROP TABLE IF EXISTS `storage_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `storage_unit` (
  `storage_id` int(11) NOT NULL,
  `capacity` int(11) DEFAULT NULL,
  `gathering_area_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`storage_id`),
  KEY `gathering_area_id` (`gathering_area_id`),
  CONSTRAINT `storage_unit_ibfk_1` FOREIGN KEY (`gathering_area_id`) REFERENCES `gathering_area` (`gathering_area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage_unit`
--

LOCK TABLES `storage_unit` WRITE;
/*!40000 ALTER TABLE `storage_unit` DISABLE KEYS */;
/*!40000 ALTER TABLE `storage_unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `supplier` (
  `supplier_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'Weapon Supplier','32.148459, 34.830008'),(2,'Food Supplier','32.179586, 34.829493'),(3,'Sanitation Supplier','32.107754, 34.874179'),(4,'Uniform Supplier','31.962669, 34.884412');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vehicle` (
  `vehicle_id` int(11) NOT NULL,
  `capacity` int(11) DEFAULT NULL,
  `availability` bit(1) DEFAULT NULL,
  PRIMARY KEY (`vehicle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle`
--

LOCK TABLES `vehicle` WRITE;
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
INSERT INTO `vehicle` VALUES (1,1000,_binary ''),(2,1500,_binary '');
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'project_db'
--

--
-- Dumping routines for database 'project_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-23 18:08:01
