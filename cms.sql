-- MySQL dump 10.13  Distrib 5.7.34, for osx10.17 (x86_64)
--
-- Host: xxxx    Database: cms
-- ------------------------------------------------------
-- Server version	5.7.33

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
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) NOT NULL,
  `cate_id` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `recommend` tinyint(4) NOT NULL DEFAULT '0',
  `title` varchar(255) NOT NULL,
  `time` varchar(200) DEFAULT NULL,
  `author` varchar(255) NOT NULL,
  `views` int(11) NOT NULL DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `detail` text NOT NULL,
  `header` varchar(255) DEFAULT NULL,
  `files` text,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COMMENT='文章';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (22,4,16,1,0,'test','2021-04-16 11:53:45','test',40,'test','<p>test</p>','https://ahqj.oss-cn-shanghai.aliyuncs.com/2021/04/16/ad06bd2ea56c4749bf18907f2f293476.jpg',NULL,'2021-04-16 03:55:48','2021-06-16 12:17:24');
INSERT INTO `article` VALUES (23,4,14,1,0,'test','2021-04-16 12:06:22','test',59,'2','<p>3</p>','https://ahqj.oss-cn-shanghai.aliyuncs.com/2021/04/16/2512a3b97fdd4f898c30712a21e4286f.jpg',NULL,'2021-04-16 04:06:36','2021-06-18 02:39:27');
INSERT INTO `article` VALUES (24,4,16,0,0,'中共大会','2021-04-15 00:00:00','小明',32,'，，，，，，','<p>召开中共大会</p>',NULL,NULL,'2021-04-16 04:26:32','2021-06-16 12:16:51');
INSERT INTO `article` VALUES (25,4,15,2,0,'五一劳动节','2021-04-23 00:00:00','李四',51,'迎接五一劳动节','<p>是东方博客不可能</p>',NULL,'[\"https://ahqj.oss-cn-shanghai.aliyuncs.com/2021/04/19/c77f9f1c51554d6397bf5e9071dfefa8.mp4\"]','2021-04-16 04:32:38','2021-06-16 12:16:49');
INSERT INTO `article` VALUES (26,4,15,2,0,'六哟儿童环节·','2021-04-20 00:00:00','王五',10,'，，，','<p>祝所有小朋友快乐</p>',NULL,NULL,'2021-04-16 04:49:42','2021-06-01 00:54:13');
INSERT INTO `article` VALUES (27,4,14,1,1,'测试公司要闻','2021-04-06 00:00:00','tom',20,'，，，，，，，','<p>是的v笨手笨脚</p>',NULL,'[]','2021-04-16 06:56:18','2021-10-15 09:17:49');
INSERT INTO `article` VALUES (28,4,14,1,1,'测试公司要问02','2021-04-12 00:00:00','同盟',13,'，，，，','<p>不是演习</p>',NULL,'[]','2021-04-16 06:58:06','2021-10-15 09:18:14');
INSERT INTO `article` VALUES (29,4,14,2,0,'测试gongsiyaowen03','2021-04-27 00:00:00','tom03',59,'，，，，','<p>这部演习</p>',NULL,NULL,'2021-04-16 06:58:52','2021-06-01 00:54:18');
INSERT INTO `article` VALUES (30,4,19,2,0,'测试企业招标','2021-04-27 00:00:00','jerry',7,'，，，，，，，','<p>jerry</p>',NULL,NULL,'2021-04-16 07:00:36','2021-06-01 00:54:19');
INSERT INTO `article` VALUES (31,4,19,2,0,'测试05','2021-04-28 00:00:00','jerry05',71,'，，，，，，','<p>jeerry05</p>','https://ahqj.oss-cn-shanghai.aliyuncs.com/2021/04/16/319c236c6f124f2fa69c98a12b04496f.jpg','[]','2021-04-16 07:01:34','2021-06-01 00:54:20');
INSERT INTO `article` VALUES (32,4,16,1,0,'测试06','2021-05-01 00:00:00','jerry',104,'，，，，，','<p><a title=\"百度\" href=\"www.baidu.com\">百度</a>fdsfdsfad</p>\n<p>dfdsaffdsfd</p>\n<p>dfdsa<a title=\"百度\">百度</a></p>\n<p>fdf<img src=\"https://ahqj.oss-cn-shanghai.aliyuncs.com/2021/04/16/2969f0bb35774898aa6b5f3c42ff8920.jpg\" data-key=\"Fri Apr 16 2021 15:26:46 GMT+0800 (中国标准时间)400\" />fd</p>','https://ahqj.oss-cn-shanghai.aliyuncs.com/2021/04/17/06a4eeea104646f3b2d1ae3b748af807.jpeg','[\"https://ahqj.oss-cn-shanghai.aliyuncs.com/2021/04/17/24a7e0f52b26401994765408e8067d8e.jpg\",\"https://ahqj.oss-cn-shanghai.aliyuncs.com/2021/04/17/1e953fb4685145ce922621746e5a06fd.jpeg\"]','2021-04-16 07:08:10','2021-10-14 11:21:03');
INSERT INTO `article` VALUES (33,4,16,1,0,'测试header','2021-04-20 00:00:00','小明',8,'，，，，','<p>，，，</p>',NULL,NULL,'2021-04-16 07:12:48','2021-06-01 00:54:25');
INSERT INTO `article` VALUES (34,4,16,1,0,'测试header02','2021-04-14 00:00:00','jerry07',6,'，，，，，，，','<p>/0000000000</p>',NULL,NULL,'2021-04-16 07:13:53','2021-06-18 03:15:09');
INSERT INTO `article` VALUES (35,4,16,1,1,'测试title ，是地方看看v比赛开局并加快步伐v就开始可不能都可能被发BNSF可能了的法律明白了你','2021-04-13 00:00:00','测试01',21,'地方','<p>是非不分说呢</p>',NULL,'[]','2021-04-16 07:17:18','2021-10-15 09:18:23');
INSERT INTO `article` VALUES (36,4,16,1,0,'test','2021-04-21 00:00:00','test02',8,'真实的VS的不能考虑','<p>豆瓣那是童年往事</p>',NULL,NULL,'2021-04-16 07:19:41','2021-06-18 02:43:10');
INSERT INTO `article` VALUES (37,4,16,1,1,'及继续测试走进建设','2021-04-12 00:00:00','反对v部分',10,'二百和人·','<p>对方纳税人纳税</p>',NULL,'[]','2021-04-16 07:21:33','2021-10-15 09:17:54');
INSERT INTO `article` VALUES (38,4,20,1,0,'测试招标信息','2021-04-14 00:00:00','是德国',30,'三百五十','<p>都难不倒你是东北&middot;</p>',NULL,NULL,'2021-04-16 07:38:46','2021-08-16 07:37:30');
INSERT INTO `article` VALUES (39,4,18,2,0,'测试i文化内涵','2021-04-21 00:00:00','失败',8,'你的','<p>发给你输入模式的吗</p>',NULL,NULL,'2021-04-16 07:40:00','2021-06-01 00:54:35');
INSERT INTO `article` VALUES (40,4,16,1,0,'继续测试走进建设01','2021-04-14 00:00:00','手动功能是',5,'阿瑟和我','<p>输入条件和网上提交内容</p>',NULL,NULL,'2021-04-16 07:40:42','2021-07-12 00:49:09');
INSERT INTO `article` VALUES (41,4,16,1,0,'test1111111','2021-04-20 00:00:00','vbsdfb',7,',,,,,,,,,,','<p><img src=\"https://ahqj.oss-cn-shanghai.aliyuncs.com/2021/04/16/0f2277510e0146f5a55669bbb3c3f8ff.jpg\" data-key=\"Fri Apr 16 2021 16:25:19 GMT+0800 (中国标准时间)911\" /> sdigjwsganlaernrioerio</p>',NULL,NULL,'2021-04-16 08:25:59','2021-07-02 01:20:02');
INSERT INTO `article` VALUES (42,4,14,1,0,'测试html','2021-04-13 06:01:06','六五',17,'暂时不能色泽奶粉过敏呢','<p>的纳税人替你解决你大科八色可能吗NSF了年老体弱<img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://ahqj.oss-cn-shanghai.aliyuncs.com/2021/04/16/9d80c112233a4d9690cb923c28c81985.jpg\" width=\"420\" height=\"256\" data-key=\"Fri Apr 16 2021 17:01:36 GMT+0800 (中国标准时间)410\" /></p>',NULL,NULL,'2021-04-16 09:01:52','2021-09-29 06:51:09');
INSERT INTO `article` VALUES (43,4,23,1,0,'华为训练营','2021-04-28 00:00:00','Dr. Jeff Donahoo',46,'ICPC & 华为训练营是由国际大学生程序设计竞赛大学联盟组织（ICPCU）发起的国际项目，旨在为热爱程序设计与算法的大学生提供进阶训练以及创新的平台。华为将一如既往地支持ICPC训练营，持续开放面向未来信息终端、计算与联接领域的研究平台，为下一代问题解决者创造更多机会，帮助他们探索挑战课题领域，推动科技进步和产业发展。','<p class=\"title\" style=\"box-sizing: border-box; margin: 0px; padding: 0px; font-size: 24px; color: #221815; position: relative; z-index: 2; font-weight: bold; text-align: center; font-family: ebg-font-regular, Arial; background-color: #ffffff;\">创新的训练模式</p>\n<p class=\"p\" style=\"box-sizing: border-box; margin: 0px; padding: 30px 0px 0px; font-size: 20px; line-height: 1.8; position: relative; z-index: 1; color: #221815; font-family: ebg-font-regular, Arial; background-color: #ffffff;\">本次训练营为ICPC首个支持进阶竞赛的国际训练项目，为即将参与下一轮竞赛的选手们提供整个训练周期更多的专业指导。这种创新的训练方式使得来自世界各地的参赛选手可以一边发现问题一边解决问题。<br style=\"box-sizing: border-box; margin: 0px; padding: 0px;\" />整个训练营将分为三个阶段：一阶段为线上竞赛，二阶段为月度在线练习及比赛，三阶段为现场竞赛与产业挑战课题竞赛（视地域疫情情况而定）。期间，教练会带领赛队研讨解题方法，培养协作的氛围， 丰富同学们解决问题的能力以及团队协作的经验。本次ICPC培训营全程由华为赞助支持，将对所有成功注册的选手免费开放。</p>','https://ahqj.oss-cn-shanghai.aliyuncs.com/2021/04/17/8a8b0dadeb854244bf45d77f39fcd201.jpg','[\"https://ahqj.oss-cn-shanghai.aliyuncs.com/2021/04/17/562b1a3a568e4bbb9009309acd85201c.jpg\",\"https://ahqj.oss-cn-shanghai.aliyuncs.com/2021/04/17/52bb35a960f941509871e2ba07acf6ac.jpg\",\"https://ahqj.oss-cn-shanghai.aliyuncs.com/2021/04/17/db5035c19cef49138ab34f523f896fd7.mp4\"]','2021-04-17 07:29:01','2022-02-23 06:49:29');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;

--
-- Table structure for table `cate`
--

DROP TABLE IF EXISTS `cate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `pid` bigint(20) NOT NULL DEFAULT '0',
  `recommend` tinyint(4) NOT NULL DEFAULT '0',
  `sort` int(11) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COMMENT='分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cate`
--

/*!40000 ALTER TABLE `cate` DISABLE KEYS */;
INSERT INTO `cate` VALUES (1,4,'党政专题',0,0,1,2);
INSERT INTO `cate` VALUES (2,4,'内部专题',0,0,1,2);
INSERT INTO `cate` VALUES (3,4,'内部专题1',2,0,1,2);
INSERT INTO `cate` VALUES (4,4,'内部专题2',2,0,1,2);
INSERT INTO `cate` VALUES (6,4,'test',0,0,0,2);
INSERT INTO `cate` VALUES (7,4,'test1',6,0,0,1);
INSERT INTO `cate` VALUES (8,4,'tets',2,0,0,2);
INSERT INTO `cate` VALUES (9,4,'走进建设',9,1,1,1);
INSERT INTO `cate` VALUES (10,4,'新闻中心',22,0,0,1);
INSERT INTO `cate` VALUES (11,4,'项目建设',11,1,0,1);
INSERT INTO `cate` VALUES (12,4,'企业文化',12,1,0,1);
INSERT INTO `cate` VALUES (13,4,'招标信息',29,1,2,1);
INSERT INTO `cate` VALUES (14,4,'公司要闻',0,0,0,1);
INSERT INTO `cate` VALUES (15,4,'通知公告',0,1,0,1);
INSERT INTO `cate` VALUES (16,4,'工作动态',0,1,0,1);
INSERT INTO `cate` VALUES (17,4,'基层建设',11,1,0,1);
INSERT INTO `cate` VALUES (18,4,'文化内涵',12,0,0,2);
INSERT INTO `cate` VALUES (19,4,'企业招标',13,1,0,1);
INSERT INTO `cate` VALUES (20,4,'政府招标',13,0,0,1);
INSERT INTO `cate` VALUES (21,4,'展会活动',0,1,0,1);
INSERT INTO `cate` VALUES (22,4,'新闻与活动',0,1,0,1);
INSERT INTO `cate` VALUES (23,4,'展会活动',22,0,3,1);
INSERT INTO `cate` VALUES (24,4,'华为直播',22,1,1,1);
INSERT INTO `cate` VALUES (25,4,'媒体直播',22,0,3,1);
INSERT INTO `cate` VALUES (26,4,'1111',21,1,0,1);
INSERT INTO `cate` VALUES (27,4,'新闻中心',0,1,0,1);
INSERT INTO `cate` VALUES (28,4,'通知公告',10,0,0,1);
INSERT INTO `cate` VALUES (29,4,'招标信息',0,1,0,1);
INSERT INTO `cate` VALUES (30,4,'公告公示',0,0,5,2);
/*!40000 ALTER TABLE `cate` ENABLE KEYS */;

--
-- Table structure for table `site`
--

DROP TABLE IF EXISTS `site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `address` varchar(255) NOT NULL,
  `lat` varchar(200) DEFAULT NULL,
  `lng` varchar(200) DEFAULT NULL,
  `logo` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `beian` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `detail` text,
  `domain` varchar(255) DEFAULT NULL COMMENT '域名',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `recommend_article` varchar(255) DEFAULT NULL COMMENT '[]',
  `banner` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='站点设置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site`
--

/*!40000 ALTER TABLE `site` DISABLE KEYS */;
INSERT INTO `site` VALUES (1,'风战花石地毛q1','任敏123','贵州省遵义市其它区','qui cupidatat magna aliquip','consequat quis','laborum ex eu Excepteur minim','18122225513','t.xqrwc@qq.com','dolor elit mollit',NULL,NULL,'l.cajfomn@qq.com',1,NULL,NULL);
/*!40000 ALTER TABLE `site` ENABLE KEYS */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(120) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `role` tinyint(4) NOT NULL DEFAULT '3' COMMENT '0平台1管理员2编辑3用户',
  `site_id` bigint(20) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','$2a$10$XhfLK7lZFNlYynpUxQogte/yaQmtw0ZaEayPsxm9rvPHSXwSBLDH6','test','',0,0,1,'xxx@qq.com','110');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-16 17:15:55
