-- MySQL dump 10.13  Distrib 8.0.22, for macos10.15 (x86_64)
--
-- Host: localhost    Database: moneymanagement
-- ------------------------------------------------------
-- Server version	8.0.22

--
-- Dumping data for table `account`
--

INSERT INTO `account` VALUES (1,'1234567890','CHECKING',999999999999,'2004-01-22',NULL,.2,'091000022',1);
# INSERT INTO `account` VALUES (3,'1111111111','CHECKING',10000,'2004-01-22',NULL,.2,'091000022',2),(4,'4444444444','SAVINGS',25000,'2004-01-22',NULL,.85,'091000022',2);
# INSERT INTO `account` VALUES (5,'2222222222','CHECKING',0,'2004-01-22',NULL,.2,'091000022',3);
--
-- Dumping data for table `login`
--

INSERT INTO `login` VALUES (1,'$2a$10$DM98Ynu/prVcywurljSHSOko73BKJb29RFW3vCGFYT9DBAW6Jd1W2','user1');
INSERT INTO `login` VALUES (3,'$2a$10$DM98Ynu/prVcywurljSHSOko73BKJb29RFW3vCGFYT9DBAW6Jd1W2','user3');
--
-- Dumping data for table `transaction_history`
--

 -- INSERT INTO `transaction` VALUES (1,'1245451',10.21,'2004-01-22','testing',1);

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` VALUES (1,'2012-05-05','tom@gmail.com','Peggy','Patel','G','5122264785','435435345',1);
INSERT INTO `customer` VALUES (3,'2012-05-05','tom@gmail.com','Tom','Walter','G','5122264785','435435345',3);

-- /*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Dumping data for table `address`
--

INSERT INTO `address` VALUES (1,'Bear','123 A st','Second Line','DE','19801');
INSERT INTO `address` VALUES (3,'Austin','123 A st','Second Line','Texas','19147');