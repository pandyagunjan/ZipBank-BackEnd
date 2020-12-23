-- MySQL dump 10.13  Distrib 8.0.22, for macos10.15 (x86_64)
--
-- Host: localhost    Database: moneymanagement
-- ------------------------------------------------------
-- Server version	8.0.22

--
-- Dumping data for table `account`
--

INSERT INTO `account` VALUES (1,'1245451','INVESTMENT',500000.22,'2004-01-22',12.5,'2001445',1);

--
-- Dumping data for table `login`
--

INSERT INTO `login` VALUES (1,'c51ChDMn','lmcqueen0'),(2,'9Gi1RGOg','mpentecost1');

--
-- Dumping data for table `transaction_history`
--

INSERT INTO `transaction` VALUES (1,'1245451',10.21,'2004-01-22','testing',1);

--
-- Dumping data for table `user`
--

INSERT INTO `user` VALUES (1,'2001-04-11','test@gmail.com','Antoine','Oakinfold',Null,'215-226-4008','112555545');

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Dumping data for table `address`
--

INSERT INTO `address` VALUES (1,'Philadelphia','123 A st',NULL,'Pennsylvania','19147');
