-- MySQL dump 10.13  Distrib 8.0.22, for macos10.15 (x86_64)
--
-- Host: localhost    Database: moneymanagement
-- ------------------------------------------------------
-- Server version	8.0.22

--
-- Dumping data for table `account`
--

INSERT INTO `account` VALUES (1,'CHECKING-123456','CHECKING',10000,'2004-01-22',0.2,'12455122',1),(2,'SAVINGS-1245451','SAVINGS',25000,'2004-01-22',0.85,'2001445',1);

--
-- Dumping data for table `login`
--

INSERT INTO `login` VALUES (1,'c51ChDMn','tom');

--
-- Dumping data for table `transaction_history`
--

 -- INSERT INTO `transaction` VALUES (1,'1245451',10.21,'2004-01-22','testing',1);

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` VALUES (1,'2012-05-05','tom@gmail.com','Tom','Walter','G','5122264785','435435345',1);

-- /*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Dumping data for table `address`
--

INSERT INTO `address` VALUES (1,'Philadelphia','123 A st','Second Line','Pennsylvania','19147');
