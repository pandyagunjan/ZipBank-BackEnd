-- MySQL dump 10.13  Distrib 8.0.22, for macos10.15 (x86_64)
--
-- Host: localhost    Database: moneymanagement
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_number` varchar(255) DEFAULT NULL,
  `account_type` varchar(255) DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `date_of_opening` date DEFAULT NULL,
  `interest_rate` double DEFAULT NULL,
  `routing_number` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'Account - 1245451','INVESTMENT',500000.22,'2004-01-22',12.5,'2001445',1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (1,'c51ChDMn',1,'lmcqueen0'),(2,'9Gi1RGOg',2,'mpentecost1'),(3,'fyMzNy7n8A8t',3,'amills2'),(4,'q0NbGZf',4,'bculross3'),(5,'yLQvlpxXIuNe',5,'gbloss4'),(6,'eQANN59g7',6,'mnolli5'),(7,'4vKaSDaX4K',7,'bdefau6'),(8,'7sBfdC',8,'svanyashin7'),(9,'eTS542J8XdVI',9,'bchavez8'),(10,'2cWHHxth',10,'dquig9'),(11,'GxSxDH',11,'bhercocka'),(12,'RZh8GTIgBE',12,'cfownesb'),(13,'QekocrR',13,'jboamc'),(14,'821sff3S6whi',14,'amckeggied'),(15,'yf9AAFSCz',15,'cswansburye'),(16,'Jf2UhD6N',16,'shanmerf'),(17,'1gBv9SwWynK',17,'tscutching'),(18,'4YUf6yym0k8F',18,'jskirvinh'),(19,'vwUorIBbE',19,'wpretoi'),(20,'AphCxH2MIgq',20,'bpargetterj'),(21,'6ttHlsQoigO',21,'dudenk'),(22,'GzazmP0TMu',22,'bpantherl'),(23,'fPWjg5e',23,'scoronasm'),(24,'H4TNZ5yA9',24,'cmcgurgann'),(25,'ZTZvoWjo',25,'mdirando');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_history`
--

DROP TABLE IF EXISTS `transaction_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_type` varchar(255) DEFAULT NULL,
  `balance_after_transaction` double DEFAULT NULL,
  `date_of_transaction` date DEFAULT NULL,
  `transaction_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_history`
--

LOCK TABLES `transaction_history` WRITE;
/*!40000 ALTER TABLE `transaction_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `dateofbirth` date DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `socialsecurity` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'48 Haas Street','2001-04-11','Antoine','Oakinfold','112-555-545'),(2,'77756 Cascade Circle','1986-04-07','Yanaton','Sikorski','125-784-457'),(3,'8 Quincy Place','1988-05-14','Warde','Tilburn','154-784-541'),(4,'77413 Milwaukee Crossing','2002-04-14','Dario','Purches','451-457-581'),(5,'4173 Arapahoe Parkway','1993-05-27','Tobi','Balfre','451-874-542'),(6,'1 Village Green Avenue','1993-09-15','Lexine','Shelliday','125-784-541'),(7,'9893 Vidon Way','1978-11-09','Morey','Vela','487-458-987'),(8,'9337 Erie Park','2001-05-25','Noella','Skein','451-784-541'),(9,'9 Towne Alley','1986-08-07','Isaac','Marskell','154-784-451'),(10,'9967 Hovde Parkway','1983-07-17','Kerwin','Franckton','154-784-451'),(11,'97169 Beilfuss Pass','1988-05-31','Almeda','Lumbers','125-457-845'),(12,'6 Mayfield Point','1990-03-09','Maude','Dunn','125-487-458'),(13,'46 Anthes Road','1979-10-01','Deni','Shermore','124-784-458'),(14,'4 Sloan Avenue','2002-01-11','Jobi','Ellick','451-458-457'),(15,'163 Lake View Circle','2004-08-22','Wallie','Leyrroyd','457-548-552'),(16,'2796 Kipling Place','1981-04-20','Augustine','Shermore','154-578-945'),(17,'4 Almo Avenue','1999-09-01','Ward','Rummins','547-854-999'),(18,'5 Mcbride Parkway','1985-11-10','Gar','Duddridge','451-457-845'),(19,'39 Bultman Hill','2001-09-26','Sosanna','MacGarvey','111-245-784'),(20,'638 Shasta Terrace','1980-08-18','Greta','Cheales','4512-457-451'),(21,'17646 Sundown Avenue','2003-05-08','Reilly','McIlwreath','245-445-444'),(22,'9 Oak Valley Drive','1995-02-27','Dona','Hancke','457-445-444'),(23,'7 Debs Plaza','1986-06-05','Georgette','Marusyak','457-444-999'),(24,'87489 Dunning Junction','1991-06-06','Dasi','Allso','451-451-444'),(25,'22 Everett Way','1995-06-20','Di','Izkovitz','987-444-111');
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

-- Dump completed on 2020-12-20 13:18:02
