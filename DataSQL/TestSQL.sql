-- MySQL dump 10.13  Distrib 8.0.38, for macos14 (x86_64)
--
-- Host: localhost    Database: library
-- ------------------------------------------------------
-- Server version	9.0.1

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `Emp_id` varchar(10) NOT NULL,
  `Emp_name` varchar(30) DEFAULT NULL,
  `Position` varchar(30) DEFAULT NULL,
  `Salary` decimal(10,2) DEFAULT NULL,
  `branch_no` varchar(10) DEFAULT NULL,
  `Admin_name` char(50) NOT NULL,
  `Password` char(50) NOT NULL,
  PRIMARY KEY (`Emp_id`),
  KEY `branch_no` (`branch_no`),
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`branch_no`) REFERENCES `branch` (`Branch_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('E101','John Doe','Manager',60000.00,'B001','E101','123456'),('E102','Jane Smith','Clerk',45000.00,'B001','E102','123456'),('E103','Mike Johnson','Librarian',55000.00,'B001','E103','123456'),('E104','Emily Davis','Assistant',40000.00,'B001','E104','123456'),('E105','Sarah Brown','Assistant',42000.00,'B002','E105','123456'),('E106','Michelle Ramirez','Assistant',43000.00,'B003','E106','123456'),('E107','Michael Thompson','Manager',62000.00,'B002','E107','123456'),('E108','Jessica Taylor','Clerk',46000.00,'B002','E108','123456'),('E109','Daniel Anderson','Librarian',57000.00,'B002','E109','123456'),('E110','Laura Martinez','Assistant',41000.00,'B004','E110','123456'),('E111','Christopher Lee','Manager',65000.00,'B003','E111','123456');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `ISBN` varchar(25) NOT NULL,
  `Book_title` varchar(80) DEFAULT NULL,
  `Category` varchar(30) DEFAULT NULL,
  `Rental_Price` decimal(10,2) DEFAULT NULL,
  `Status` enum('Yes','No') DEFAULT NULL,
  `Author` varchar(30) DEFAULT NULL,
  `Publisher` varchar(30) DEFAULT NULL,
  `Total_quantity` int DEFAULT NULL,
  `Available_quantity` int DEFAULT NULL,
  PRIMARY KEY (`ISBN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES ('978-0-09-957807-9','A Game of Thrones','Fantasy',7.50,'Yes','George R.R. Martin','Bantam',10,10),('978-0-14-044930-3','The Histories','History',5.50,'Yes','Herodotus','Penguin Classics',8,8),('978-0-14-118776-1','One Hundred Years of Solitude','Literary Fiction',6.50,'Yes','Gabriel Garcia Marquez','Penguin Books',8,8),('978-0-141-44171-6','Jane Eyre','Classic',4.00,'Yes','Charlotte Bronte','Penguin Classics',4,4),('978-0-19-280551-1','The Guns of August','History',7.00,'Yes','Barbara W. Tuchman','Oxford University Press',7,7),('978-0-307-37840-1','The Alchemist','Fiction',2.50,'Yes','Paulo Coelho','HarperOne',15,15),('978-0-307-58837-1','Sapiens: A Brief History of Humankind','History',8.00,'Yes','Yuval Noah Harari','Harper Perennial',12,12),('978-0-330-25864-8','Animal Farm','Classic',5.50,'Yes','George Orwell','Penguin Books',5,5),('978-0-375-41398-8','The Diary of a Young Girl','History',6.50,'Yes','Anne Frank','Bantam',6,6),('978-0-393-05081-8','A People\'s History of the United States','History',9.00,'Yes','Howard Zinn','Harper Perennial',5,5),('978-0-393-91257-8','Guns, Germs, and Steel: The Fates of Human Societies','History',7.00,'Yes','Jared Diamond','W. W. Norton & Company',7,7),('978-0-525-47535-5','The Great Gatsby','Classic',8.00,'Yes','F. Scott Fitzgerald','Scribner',10,10),('978-0-553-29698-2','The Catcher in the Rye','Classic',7.00,'Yes','J.D. Salinger','Little, Brown and Company',10,10),('978-0-679-76489-8','Harry Potter and the Sorcerer\'s Stone','Fantasy',7.00,'Yes','J.K. Rowling','Scholastic',20,20),('978-0-7432-4722-4','The Da Vinci Code','Mystery',8.00,'Yes','Dan Brown','Doubleday',12,12),('978-0-7432-7357-1','1491: New Revelations of the Americas Before Columbus','History',6.50,'Yes','Charles C. Mann','Vintage Books',9,9);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BookTransactions`
--

DROP TABLE IF EXISTS `BookTransactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BookTransactions` (
  `Transaction_Id` varchar(10) NOT NULL,
  `Customer_Id` varchar(10) DEFAULT NULL,
  `Book_ISBN` varchar(25) DEFAULT NULL,
  `Issue_Date` date DEFAULT NULL,
  `Return_Date` date DEFAULT NULL,
  `Status` enum('Issued','Returned') DEFAULT 'Issued',
  `Book_title` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`Transaction_Id`),
  KEY `Customer_Id` (`Customer_Id`),
  KEY `Book_ISBN` (`Book_ISBN`),
  CONSTRAINT `booktransactions_ibfk_1` FOREIGN KEY (`Customer_Id`) REFERENCES `Customer` (`Customer_Id`) ON DELETE CASCADE,
  CONSTRAINT `booktransactions_ibfk_2` FOREIGN KEY (`Book_ISBN`) REFERENCES `Books` (`ISBN`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BookTransactions`
--

LOCK TABLES `BookTransactions` WRITE;
/*!40000 ALTER TABLE `BookTransactions` DISABLE KEYS */;
INSERT INTO `BookTransactions` VALUES ('IS101','C101','978-0-553-29698-2','2023-05-01','2023-06-06','Returned','The Catcher in the Rye'),('IS102','C102','978-0-7432-4722-4','2023-05-02','2023-06-07','Returned','The Da Vinci Code'),('IS103','C103','978-0-7432-7357-1','2023-05-03','2023-06-08','Returned','1491: New Revelations of the Americas Before Columbus'),('IS104','C104','978-0-307-58837-1','2023-05-04','2023-06-09','Returned','Sapiens: A Brief History of Humankind'),('IS105','C105','978-0-375-41398-8','2023-05-05','2023-06-10','Returned','The Diary of a Young Girl');
/*!40000 ALTER TABLE `BookTransactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Branch`
--

DROP TABLE IF EXISTS `Branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Branch` (
  `Branch_no` varchar(10) NOT NULL,
  `Manager_id` varchar(10) DEFAULT NULL,
  `Branch_address` varchar(30) DEFAULT NULL,
  `Contact_no` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`Branch_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Branch`
--

LOCK TABLES `Branch` WRITE;
/*!40000 ALTER TABLE `Branch` DISABLE KEYS */;
INSERT INTO `Branch` VALUES ('B001','M101','123 Main St','+919099988676'),('B002','M102','456 Elm St','+919099988677'),('B003','M103','789 Oak St','+919099988678'),('B004','M104','567 Pine St','+919099988679'),('B005','M105','890 Maple St','+919099988680');
/*!40000 ALTER TABLE `Branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Customer`
--

DROP TABLE IF EXISTS `Customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Customer` (
  `Customer_Id` varchar(10) NOT NULL,
  `Customer_name` varchar(30) DEFAULT NULL,
  `Customer_address` varchar(30) DEFAULT NULL,
  `Reg_date` date DEFAULT NULL,
  `User_name` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL DEFAULT '123456',
  PRIMARY KEY (`Customer_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Customer`
--

LOCK TABLES `Customer` WRITE;
/*!40000 ALTER TABLE `Customer` DISABLE KEYS */;
INSERT INTO `Customer` VALUES ('C101','Alice Johnson','123 Main St','2021-05-15','C101','123456'),('C102','Bob Smith','456 Elm St','2021-06-20','C102','123456'),('C103','Carol Davis','789 Oak St','2021-07-10','C103','123456'),('C104','Dave Wilson','567 Pine St','2021-08-05','C104','123456'),('C105','Eve Brown','890 Maple St','2021-09-25','C105','123456'),('C106','Frank Thomas','234 Cedar St','2021-10-15','C106','123456'),('C107','Grace Taylor','345 Walnut St','2021-11-20','C107','123456'),('C108','Henry Anderson','456 Birch St','2021-12-10','C108','123456'),('C109','Ivy Martinez','567 Oak St','2022-01-05','C109','123456'),('C110','Jack Wilson','678 Pine St','2022-02-25','C110','123456'),('C111',NULL,NULL,'2024-10-29','long','1234');
/*!40000 ALTER TABLE `Customer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-29 19:42:40
