-- MySQL dump 10.13  Distrib 5.6.19, for Win64 (x86_64)
--
-- Host: localhost    Database: funnyacm
-- ------------------------------------------------------
-- Server version	5.6.22-enterprise-commercial-advanced-log

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

CREATE DATABASE funnyacm;
USE funnyacm;

--
-- Table structure for table `contest_info`
--

DROP TABLE IF EXISTS `contest_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contest_info` (
  `cot_id` int(32) NOT NULL AUTO_INCREMENT,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `season_list` varchar(128) NOT NULL,
  `cot_name` varchar(45) NOT NULL,
  `prb_list` varchar(128) NOT NULL,
  PRIMARY KEY (`cot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contest_info`
--


--
-- Table structure for table `contest_submit`
--

DROP TABLE IF EXISTS `contest_submit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contest_submit` (
  `cot_id` int(32) NOT NULL,
  `smt_id` int(32) NOT NULL,
  PRIMARY KEY (`smt_id`),
  KEY `contest_submit_cot_id` (`cot_id`),
  CONSTRAINT `contest_submit_PK` FOREIGN KEY (`cot_id`) REFERENCES `contest_info` (`cot_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contest_submit`
--


--
-- Table structure for table `judge_result`
--

DROP TABLE IF EXISTS `judge_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `judge_result` (
  `smt_id` int(32) NOT NULL,
  `jdg_res` varchar(20) NOT NULL,
  `jdg_detail` text NOT NULL,
  `jdg_run_memory` varchar(45) NOT NULL,
  `jdg_run_time` varchar(45) NOT NULL,
  PRIMARY KEY (`smt_id`),
  CONSTRAINT `jdg_id` FOREIGN KEY (`smt_id`) REFERENCES `submit_record` (`smt_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `judge_result`
--


--
-- Table structure for table `problem_detail`
--

DROP TABLE IF EXISTS `problem_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem_detail` (
  `prb_id` int(32) NOT NULL,
  `title` varchar(45) NOT NULL,
  `time_limit` varchar(45) NOT NULL,
  `memory_limit` varchar(45) NOT NULL,
  `prb_detail` text NOT NULL,
  `input_detail` text NOT NULL,
  `output_detail` text NOT NULL,
  `sample_input` text NOT NULL,
  `sample_output` text NOT NULL,
  `hint_detail` text NOT NULL,
  PRIMARY KEY (`prb_id`),
  CONSTRAINT `problem_detail_PK` FOREIGN KEY (`prb_id`) REFERENCES `problem_info` (`prb_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem_detail`
--

LOCK TABLES `problem_detail` WRITE;
/*!40000 ALTER TABLE `problem_detail` DISABLE KEYS */;
INSERT INTO `problem_detail` VALUES (1,'Circle Game','1000MS','131072KB','You must have some knowledge with circle games in ACM problems, such as Joseph Ring problem. Today we are going to introduce a new circle game described as follows. There is a circle which contains \n<strong>M</strong> points, numbered from 0 to M-1. At first, n students stand in different points of the circle. It is possible that more than one student stand in the same point. For each second, each student moves one step forward in clockwise direction. The following picture gives us an example of that M equals to 8 and n equals to 3. Initially, the first student S1 stands in the point 0, the second student S2 stands in the point 2 and the third student stands in the point 7. After 2 seconds, they will change their positions as the right part of the picture.\n<br />\n<center>\n <img src=\"http://acm.hdu.edu.cn/data/images/C468-1002-1.jpg\" />\n</center>\n<br /> At the beginning of the game, Tracy writes down the positions of all students. Then he will go to sleep and students will abide by the above rules to make this game run until Tracy wake up. After a period of time, Tracy wakes up. He finds it is very hard to identify where the first student S1 stands in and where the second student S2 stands in and so on, because all students are the exactly similar and unable to be distinguished. So Tracy records all the positions of students and writes down the XOR sum of these positions (The XOR sum of an array A means A[0] xor A[1] xor … A[n-2] xor A[n-1] and xor is the exclusive or operation, for example 0111 xor 1011 = 1100). Now Tracy wants you to help him to know how many seconds he had slept. To simplify the problem, the number of points is always a power of 2 like 2\n<sup>m</sup>. \n<br /> Furthermore, Tracy knows that the time he slept is not greater than \n<strong>T</strong>. Please note that the time Tracy slept must be greater than zero. There may have many different periods of time corresponding with the above conditions.','　　There are several test cases, each test case firstly contains four integers \n<strong>n, m, S, T</strong>. S is the XOR sum of students’ positions when Tracy wakes up. Another 3 integers n, m, T are described as the above. Then n integers will follow in the next line, which represent the positions of students initially. The input will finish with the end of file.\n<br />0 &lt; n &lt;= 100000, 0 &lt; m &lt;= 50, 0 &lt;= S&lt; 2\n<sup>50</sup>, 0 &lt; T &lt;= 10\n<sup>16</sup> and the student position is in the range [0, 2\n<sup>m</sup>).','　　For each test case, output the number of possible periods of time that Tracy had slept and matched the above restrictions. Please output zero if no time matched.','<pre>\n \n  3 3 7 5\n0 2 7\n5 3 7 5\n1 2 3 4 5\n4 4 0 10\n1 3 5 7\n6 5 18 100\n22 10 18 20 2 10\n </pre>','<pre>\n \n  1\n0\n4\n50\n\n  \n   \n    </pre>','<pre>\n    　　For the first test case, the following table explains that only “2 seconds” matched the restrictions. \n   <center>\n    <img src=\"http://acm.hdu.edu.cn/data/images/C468-1002-2.jpg\" />\n   </center> \n  \n  <i style=\"font-size:1px\"> </i>\n </pre>');
/*!40000 ALTER TABLE `problem_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem_info`
--

DROP TABLE IF EXISTS `problem_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem_info` (
  `prb_id` int(32) NOT NULL AUTO_INCREMENT,
  `oj_id` varchar(16) NOT NULL,
  `oj_name` varchar(8) NOT NULL,
  `submit_params` varchar(16) NOT NULL,
  PRIMARY KEY (`prb_id`),
  KEY `oj_name_And_oj_id` (`oj_name`,`oj_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem_info`
--

LOCK TABLES `problem_info` WRITE;
/*!40000 ALTER TABLE `problem_info` DISABLE KEYS */;
INSERT INTO `problem_info` VALUES (1,'4566','HDOJ','4566');
/*!40000 ALTER TABLE `problem_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `season_apply`
--

DROP TABLE IF EXISTS `season_apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `season_apply` (
  `sea_id` int(32) NOT NULL,
  `user_id` varchar(16) NOT NULL,
  `status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`sea_id`,`user_id`),
  KEY `season_apply_userPK_idx` (`user_id`),
  CONSTRAINT `season_apply_PK` FOREIGN KEY (`sea_id`) REFERENCES `season_info` (`sea_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `season_apply_userPK` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `season_apply`
--

LOCK TABLES `season_apply` WRITE;
/*!40000 ALTER TABLE `season_apply` DISABLE KEYS */;
/*!40000 ALTER TABLE `season_apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `season_info`
--

DROP TABLE IF EXISTS `season_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `season_info` (
  `sea_id` int(32) NOT NULL AUTO_INCREMENT,
  `sea_des` text,
  `sea_name` varchar(45) NOT NULL,
  `sea_len` int(32) NOT NULL,
  `sea_create` datetime NOT NULL,
  PRIMARY KEY (`sea_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `season_info`
--



--
-- Table structure for table `season_rating`
--

DROP TABLE IF EXISTS `season_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `season_rating` (
  `sea_id` int(32) NOT NULL,
  `user_id` varchar(16) NOT NULL,
  `rating` int(32) NOT NULL DEFAULT '1500',
  `join_cnt` int(32) NOT NULL,
  PRIMARY KEY (`sea_id`,`user_id`),
  KEY `season_rating_user_idx` (`user_id`),
  CONSTRAINT `season_rating_PK` FOREIGN KEY (`sea_id`) REFERENCES `season_info` (`sea_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `season_rating_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `season_rating`
--


--
-- Table structure for table `submit_code`
--

DROP TABLE IF EXISTS `submit_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `submit_code` (
  `smt_id` int(32) NOT NULL,
  `code` text,
  PRIMARY KEY (`smt_id`),
  CONSTRAINT `code_id` FOREIGN KEY (`smt_id`) REFERENCES `submit_record` (`smt_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `submit_code`
--



--
-- Table structure for table `submit_record`
--

DROP TABLE IF EXISTS `submit_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `submit_record` (
  `smt_id` int(32) NOT NULL,
  `smt_oj_id` varchar(32) NOT NULL,
  `smt_time` datetime NOT NULL,
  `smt_user` varchar(16) NOT NULL,
  `smt_oj` varchar(8) NOT NULL,
  `prb_id` int(32) NOT NULL,
  PRIMARY KEY (`smt_id`),
  KEY `smt_user` (`smt_user`,`prb_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `submit_record`
--


--
-- Table structure for table `task_info`
--

DROP TABLE IF EXISTS `task_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_info` (
  `task_id` int(32) NOT NULL,
  `condition_mask` int(32) NOT NULL DEFAULT '0',
  `start_time` datetime NOT NULL,
  `task_tpl_id` int(32) NOT NULL,
  `task_name` varchar(45) NOT NULL,
  `task_description` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `task_tpl_id_FK` (`task_tpl_id`),
  CONSTRAINT `task_tpl_id_FK` FOREIGN KEY (`task_tpl_id`) REFERENCES `task_template` (`task_tpl_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_info`
--

LOCK TABLES `task_info` WRITE;
/*!40000 ALTER TABLE `task_info` DISABLE KEYS */;
INSERT INTO `task_info` VALUES (0,0,'2015-04-20 00:00:00',0,'#Built-in task#',"don't remove");
/*!40000 ALTER TABLE `task_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_submit`
--

DROP TABLE IF EXISTS `task_submit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_submit` (
  `task_id` int(32) NOT NULL,
  `smt_id` int(32) NOT NULL,
  PRIMARY KEY (`task_id`,`smt_id`),
  KEY `smt_id_FK_idx` (`smt_id`),
  CONSTRAINT `smt_id_FK` FOREIGN KEY (`smt_id`) REFERENCES `submit_record` (`smt_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `task_id_FK` FOREIGN KEY (`task_id`) REFERENCES `task_info` (`task_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_submit`
--


--
-- Table structure for table `task_template`
--

DROP TABLE IF EXISTS `task_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_template` (
  `task_tpl_id` int(32) NOT NULL AUTO_INCREMENT,
  `prb_id_list` varchar(128) NOT NULL,
  `task_tpl_name` varchar(45) NOT NULL,
  PRIMARY KEY (`task_tpl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_template`
--

LOCK TABLES `task_template` WRITE;
/*!40000 ALTER TABLE `task_template` DISABLE KEYS */;
INSERT INTO `task_template` VALUES (0,'1;1;1;1','#session 1#'),(1,'1;1;1;1','#session 2#'),(2,'1;1;1;1','#session 3#');
/*!40000 ALTER TABLE `task_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` varchar(16) NOT NULL,
  `user_type` int(2) NOT NULL,
  `user_psw` varchar(32) NOT NULL,
  `user_name` varchar(10) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('test1',1,'202CB962AC59075B964B07152D234B70','Alice'),('test2',0,'202CB962AC59075B964B07152D234B70','Bob'),('funnyacm',2,'202CB962AC59075B964B07152D234B70','admin');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_do_task`
--

DROP TABLE IF EXISTS `user_do_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_do_task` (
  `task_id` int(32) NOT NULL,
  `user_id` varchar(16) NOT NULL,
  `status` int(32) DEFAULT NULL,
  PRIMARY KEY (`task_id`,`user_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `task_id` FOREIGN KEY (`task_id`) REFERENCES `task_info` (`task_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_do_task`
--


LOCK TABLES `user_do_task` WRITE;
/*!40000 ALTER TABLE `user_do_task` DISABLE KEYS */;
INSERT INTO `user_do_task` VALUES (0,'test2',0);
/*!40000 ALTER TABLE `user_do_task` ENABLE KEYS */;
UNLOCK TABLES;

-- Dump completed on 2015-05-01  9:35:16
