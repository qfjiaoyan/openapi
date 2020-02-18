/*
SQLyog Enterprise Trial - MySQL GUI v7.11 
MySQL - 5.7.24 : Database - openapi-admin
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`openapi-admin` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `openapi-admin`;

/*Table structure for table `admin_user` */

DROP TABLE IF EXISTS `admin_user`;

CREATE TABLE `admin_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PASSWORD` varchar(256) NOT NULL,
  `EMAIL` varchar(50) NOT NULL,
  `REAL_NAME` varchar(20) NOT NULL,
  `STATUS` smallint(6) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `admin_user` */

insert  into `admin_user`(`ID`,`PASSWORD`,`EMAIL`,`REAL_NAME`,`STATUS`) values (1,'111111','admin','超级管理员',1),(2,'666666','menglili@shiro.com','3333',1),(3,'111111','test@shiro.com','æµ‹è¯•',1),(4,'A0F73C801EC01FC62D5BD046BCB775AA','aa@shiro.com','13717967',0),(5,'A0F73C801EC01FC62D5BD046BCB775AA','bb@shiro.com','æµ‹è¯•',0),(6,'A8BDD2E90BBAE6B555C56E6F30B9E3D4','cc@shiro.com','æµ‹è¯•',0),(7,'111111','dasdasd','sad',1),(8,'dasdasdsa','dsadsadsa','adasdsad',1),(9,'dasdsadsa','asddasdsa','ddasdsa',0),(10,'sadasdsa','asdsad','asdsadsa',0),(11,'dsadsad','dasdas','dasdsa',1),(12,'000000','000','000',1),(13,'safsaf','sadsa','saffsaf',1),(14,'111111','79879','35325',1),(15,'asfasfs','czxczx','ccasa',1),(16,'5555555','dasda','5555',1),(17,'adasd','asdasda','asdas',1);

/*Table structure for table `api_mapping` */

DROP TABLE IF EXISTS `api_mapping`;

CREATE TABLE `api_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gatewayApiName` varchar(100) NOT NULL COMMENT '对外接口名称',
  `insideApiUrl` varchar(100) NOT NULL COMMENT '对内的接口URL',
  `state` tinyint(4) NOT NULL COMMENT 'api状态 1 可用 0 不可用 ',
  `description` varchar(500) DEFAULT NULL COMMENT '接口描述',
  `serviceId` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_openapiname` (`gatewayApiName`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `api_mapping` */

insert  into `api_mapping`(`id`,`gatewayApiName`,`insideApiUrl`,`state`,`description`,`serviceId`) values (1,'taobao.pop.order.get','xxx',1,'获取订单信息接口','222'),(2,'taobao.order.order.update','xxxx',1,'修改订单状态接口','daseqweq'),(3,'asd','sada',0,'231','21');

/*Table structure for table `app_info` */

DROP TABLE IF EXISTS `app_info`;

CREATE TABLE `app_info` (
  `id` int(11) NOT NULL COMMENT '客户ID',
  `corpName` varchar(50) NOT NULL COMMENT '公司名称',
  `appName` varchar(50) NOT NULL COMMENT 'app名称',
  `appKey` varchar(50) NOT NULL COMMENT 'APPKEY',
  `appSecret` varchar(50) NOT NULL COMMENT 'APP密码',
  `redirectUrl` varchar(100) NOT NULL COMMENT '回调地址',
  `limit` int(11) NOT NULL COMMENT 'APP每天限流量',
  `description` varchar(500) DEFAULT NULL COMMENT 'APP描述',
  PRIMARY KEY (`id`),
  KEY `idx_corpname` (`appKey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `app_info` */

insert  into `app_info`(`id`,`corpName`,`appName`,`appKey`,`appSecret`,`redirectUrl`,`limit`,`description`) values (1,'海尔集团','海尔电器ERP','561AC1A8676CFCB0CC61B041AE42ABB8','ff2ff43192a84bb49988720c307182c8','https://www.haier.com/cn/',300000,'desc');

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  `URL` varchar(500) DEFAULT NULL,
  `ICON` varchar(30) DEFAULT NULL,
  `PERMS` varchar(100) DEFAULT NULL,
  `TYPE` smallint(6) DEFAULT NULL,
  `SORT` int(11) DEFAULT '1000',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Data for the table `menu` */

insert  into `menu`(`ID`,`NAME`,`PARENT_ID`,`URL`,`ICON`,`PERMS`,`TYPE`,`SORT`) values (2,'权限管理',NULL,NULL,'fa fa-bug',NULL,0,1),(3,'菜单管理',2,'/sys/menu.html','fa fa-th-list','sys:menu:list',1,1000),(4,'角色管理',2,'/sys/role.html','fa fa-key','sys:user:list',1,1000),(5,'用户管理',2,'/sys/user.html','fa fa-user','sys:user:list',1,1000),(6,'添加',5,NULL,NULL,'sys:user:add',2,1000),(7,'修改',5,NULL,NULL,'sys:user:update',2,1000),(8,'删除',5,NULL,NULL,'sys:user:delete',2,1000),(9,'授权',5,NULL,NULL,'sys:user:assign',2,1000),(10,'添加',3,NULL,NULL,'sys:menu:add',2,1000),(11,'修改',3,NULL,NULL,'sys:menu:update',2,1000),(12,'删除',3,NULL,NULL,'sys:menu:delete',2,1000),(16,'添加',4,NULL,NULL,'sys:role:add',2,1000),(17,'修改',4,NULL,NULL,'sys:role:update',2,1000),(18,'删除',4,NULL,NULL,'sys:role:delete',2,1000),(19,'授权',4,NULL,NULL,'sys:role:assign',2,1000),(20,'路由管理',NULL,'/sys/api_mapping.html','fa fa-cogs','sys:api:list',1,3),(21,'添加',20,NULL,NULL,'sys:api:add',2,1000),(22,'修改',20,NULL,NULL,'sys:api:update',2,1000),(23,'删除',20,NULL,NULL,'sys:api:delete',2,1000),(24,'日志搜索',NULL,'/sys/search.html','fa fa-search','sys:search:list',1,5),(25,'token管理',NULL,'/sys/token.html','fa fa-cubes','sys:token:list',1,4),(26,'修改',25,NULL,NULL,'sys:token:update',2,1000),(27,'应用管理',NULL,'/sys/app_info.html','fa fa-diamond','sys:app:list',1,2),(28,'修改',27,NULL,NULL,'sys:app:update',2,1000),(29,'添加',25,NULL,NULL,'sys:token:add',2,1);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) NOT NULL,
  `REMARK` varchar(50) NOT NULL,
  `STATUS` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`ID`,`NAME`,`REMARK`,`STATUS`) values (1,'admin','超级管理员',1),(2,'test','remark',1),(3,'aa','333',0),(4,'asd','sadsa',0);

/*Table structure for table `role_menu` */

DROP TABLE IF EXISTS `role_menu`;

CREATE TABLE `role_menu` (
  `ROLE_ID` int(11) NOT NULL,
  `MENU_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_menu` */

insert  into `role_menu`(`ROLE_ID`,`MENU_ID`) values (1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(1,28),(1,29),(2,2),(2,4),(2,5),(2,6),(2,7),(2,8),(2,16),(2,17),(2,18);

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_role` */

insert  into `user_role`(`USER_ID`,`ROLE_ID`) values (1,1),(2,2),(2,3),(3,1);

/*Table structure for table `user_token` */

DROP TABLE IF EXISTS `user_token`;

CREATE TABLE `user_token` (
  `id` int(11) NOT NULL,
  `appId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `access_token` varchar(50) NOT NULL,
  `expireTime` datetime NOT NULL,
  `startTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_token` */

insert  into `user_token`(`id`,`appId`,`userId`,`access_token`,`expireTime`,`startTime`) values (1,123,312,'eb6aa496-4918-4099-9082-19582e8438e1','2019-05-05 00:00:00','2019-08-02 00:00:00');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
