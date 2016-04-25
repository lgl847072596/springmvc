-- MySQL dump 10.13  Distrib 5.7.11, for osx10.10 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.7.11

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
-- Table structure for table `Tb_App`
--

DROP TABLE IF EXISTS `Tb_App`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tb_App` (
  `appId` varchar(200) NOT NULL,
  `appName` varchar(100) DEFAULT NULL,
  `packageName` varchar(100) NOT NULL,
  `platform` varchar(20) NOT NULL,
  `versionCode` int(11) DEFAULT NULL,
  `versionName` varchar(20) DEFAULT NULL,
  `log` varchar(500) DEFAULT NULL,
  `forceUpdate` varchar(10) DEFAULT NULL,
  `downloadCount` mediumtext,
  `createTime` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `mappingUrl` varchar(200) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `belongAccount` varchar(40) NOT NULL,
  `categoryId` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tb_App`
--

LOCK TABLES `Tb_App` WRITE;
/*!40000 ALTER TABLE `Tb_App` DISABLE KEYS */;
INSERT INTO `Tb_App` VALUES ('0c347c00-3b22-4a56-a42c-4fe7bcea181e','三明第一医院','com.ylzinfo.palmhospital.smdyyy','android',330,'330','首次创建','false','1','2016-04-25 16:48:47','2016-04-25 16:53:23','http://www.191cn.com/apk/PalmHospital_sdmyyy.apk','http://www.191cn.com/apk/','15106079425',''),('0f8ded57-8063-4467-8706-f338be30752d','福建沙县医院','com.ylzinfo.palmhospital.smsx','android',330,'330','首次创建','false','0','2016-04-25 16:48:47','2016-04-25 16:53:52','http://www.191cn.com/apk/PalmHospital_smsx.apk','http://www.191cn.com/apk/','15106079425',''),('12d94e08-6f5b-401d-a772-87d8a581098a','易就医省口腔版','com.ylzinfo.palmhospital.fjkq','android',330,'330','首次创建','false','0','2016-04-25 16:48:47','2016-04-25 16:54:49','http://www.191cn.com/apk/PalmHospital_fjkq.apk','http://www.191cn.com/apk/','15106079425',''),('2f41ec86-7bbe-4af8-9582-622585848307','易就医省康复版','com.ylzinfo.palmhospital.fjkf','android',330,'330','首次创建','false','0','2016-04-25 16:48:47','2016-04-25 16:54:59','http://www.191cn.com/apk/PalmHospital_fjkf.apk','http://www.191cn.com/apk/','15106079425',''),('53cd7bff-4890-4fcf-8b67-3008f316135c','易就医省三版','com.ylzinfo.palmhospital.fjsrm3','android',330,'330','首次创建','false','0','2016-04-25 16:48:47','2016-04-25 16:55:29','http://www.191cn.com/apk/PalmHospital_fjsrm3.apk','http://www.191cn.com/apk/','15106079425',''),('58a08443-921b-462e-8638-afc8fe0a5aab','易就医省二版','com.ylzinfo.palmhospital.fjsrm2','android',330,'330','首次创建','false','0','2016-04-25 16:48:47','2016-04-25 16:55:42','http://www.191cn.com/apk/PalmHospital_fjsrm2.apk','http://www.191cn.com/apk/','15106079425',''),('668a9e23-7760-420a-86b1-544cbdc72a62','易就医省人民版','com.ylzinfo.palmhospital.fjsrm','android',330,'330','首次创建','false','0','2016-04-25 16:48:47','2016-04-25 16:55:52','http://www.191cn.com/apk/PalmHospital_fjsrm.apk','http://www.191cn.com/apk/','15106079425',''),('68951330-f374-4805-827c-5b54a8155671','易就医长乐医院','com.ylzinfo.palmhospital.clsyy','android',330,'330','首次创建','false','0','2016-04-25 16:48:47','2016-04-25 16:56:18','http://www.191cn.com/apk/PalmHospital_clsyy.apk','http://www.191cn.com/apk/','15106079425',''),('75abcde1-d33a-4b80-bc6e-3ee41a523af4','淄博中心医院','com.ylzinfo.palmhospital.sdzbzx','android',330,'330','首次创建','false','0','2016-04-25 16:48:47','2016-04-25 16:56:39','http://www.191cn.com/apk/PalmHospital_sdzbzx.apk','http://www.191cn.com/apk/','15106079425',''),('a96cff43-bd4d-45ee-8f4a-4af63cf4f8dc','易就医演示版','com.ylzinfo.palmhospital.demo','android',330,'330','首次创建','false','0','2016-04-25 16:48:47','2016-04-25 16:56:57','http://www.191cn.com/apk/PalmHospital-demo.apk','http://www.191cn.com/apk/','15106079425',''),('ce8a21c5-82ac-465c-81e0-90ebca75965d','易就医','com.ylzinfo.palmhospital','android',330,'330','首次创建','false','0','2016-04-25 16:48:47','2016-04-25 16:57:18','http://www.191cn.com/apk/PalmHospital.apk','http://www.191cn.com/apk/','15106079425',''),('fed0acb6-d109-4612-bbe4-383c4cca4b21','泉州市中医院','com.ylzinfo.palmhospital.fjqzzylhyy','android',330,'330','首次创建','false','0','2016-04-25 16:48:47','2016-04-25 16:58:05','http://www.191cn.com/apk/PalmHospital_fj_qz_zyy.apk','http://www.191cn.com/apk/','15106079425','');
/*!40000 ALTER TABLE `Tb_App` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-25 17:04:35
