/*
MySQL Data Transfer
Source Host: localhost
Source Database: db_weixin
Target Host: localhost
Target Database: db_weixin
Date: 2014/10/24 15:31:11
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for user_location
-- ----------------------------
CREATE TABLE `user_location` (
  `id` int(11) NOT NULL auto_increment,
  `open_id` varchar(50) default NULL,
  `lng` varchar(30) default NULL,
  `lat` varchar(30) default NULL,
  `bd09_lng` varchar(30) default NULL,
  `bd09_lat` varchar(30) default NULL,
  `label` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `user_location` VALUES ('9', 'owYx2uPGBiN7Bw8BGsBWnV-XRPe0', '114.55638734443', '38.024482007035', '114.549805', '38.018787', '????????????51?');
