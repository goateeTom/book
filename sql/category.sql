/*
 Navicat MySQL Data Transfer

 Source Server         : tf
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : goods

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 07/07/2020 13:27:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `cid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `desc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `orderBy` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`cid`) USING BTREE,
  UNIQUE INDEX `cname`(`cname`) USING BTREE,
  INDEX `FK_t_category_t_category`(`pid`) USING BTREE,
  INDEX `orderBy`(`orderBy`) USING BTREE,
  CONSTRAINT `FK_t_category_t_category` FOREIGN KEY (`pid`) REFERENCES `category` (`cid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('0cdc801f99814e0d8e8fc568dc85ee42', '童书', NULL, '儿童读物', 48);
INSERT INTO `category` VALUES ('1', '程序设计', NULL, '程序设计分类', 1);
INSERT INTO `category` VALUES ('1ec427234a644c1d9eb4ec7ca9d81159', '文学', NULL, '文学作品', 55);
INSERT INTO `category` VALUES ('2', '办公室用书', NULL, '办公室用书', 2);
INSERT INTO `category` VALUES ('3', '图形 图像 多媒体', NULL, '多媒体', 3);
INSERT INTO `category` VALUES ('34f4403a16214616a7a323b2f3b1d045', '科幻图书', NULL, '科幻类图书', 59);
INSERT INTO `category` VALUES ('3c38c5621225434fbf92d8e91a5a2198', '科技文学', '1ec427234a644c1d9eb4ec7ca9d81159', '科技文学作品', 56);
INSERT INTO `category` VALUES ('4D01FFF0CB94468EA907EF42780668AB', '购买指南 组装指南 维修', '2', '购买指南 组装指南 维修分类', 18);
INSERT INTO `category` VALUES ('5', '数据库', NULL, '数据库', 5);
INSERT INTO `category` VALUES ('50a43a5e914944efaf9ddcb9260973d7', '玄幻小说', '6c2be3e993684648861d2f91948f8cd5', '玄幻小说。', 58);
INSERT INTO `category` VALUES ('56AD72718C524147A2485E5F4A95A062', '3DS MAX', '3', '3DS MAX分类', 21);
INSERT INTO `category` VALUES ('57DE3C2DDA784B81844029A28217698C', 'Dreamweaver', '3', 'Dreamweaver分类', 24);
INSERT INTO `category` VALUES ('5F79D0D246AD4216AC04E9C5FAB3199E', 'Java Javascript', '1', 'Java Javascript分类', 10);
INSERT INTO `category` VALUES ('65640549B80E40B1981CDEC269BFFCAD', 'Photoshop', '3', 'Photoshop分类', 20);
INSERT INTO `category` VALUES ('6c2be3e993684648861d2f91948f8cd5', '小说', NULL, '小说分类', 57);
INSERT INTO `category` VALUES ('757BDAB506A445EC8DEDA4CE04303B9F', '网页设计', '3', '网页设计分类', 22);
INSERT INTO `category` VALUES ('84ECE401C2904DBEA560D04A581A66D9', 'HTML XML', '1', 'HTML XML分类', 13);
INSERT INTO `category` VALUES ('922E6E2DB04143D39C9DDB26365B3EE8', 'C C++ VC VC++', '1', 'C C++ VC VC++分类', 12);
INSERT INTO `category` VALUES ('96F209F79DB242E9B99CC1B98FAB01DB', '数据库理论', '5', '数据库理论分类', 33);
INSERT INTO `category` VALUES ('99f1c3788a4e4ec78e517fdcb08eec9b', '少儿读物', '0cdc801f99814e0d8e8fc568dc85ee42', '少儿睡前读书', 49);
INSERT INTO `category` VALUES ('9ca61a7aa28b42f298f116fe80f1270f', '科幻漫画', '34f4403a16214616a7a323b2f3b1d045', '科幻漫画', 60);
INSERT INTO `category` VALUES ('B92ED191DBE647BE8F75721FB231E207', '因特网 电子邮件', '2', '因特网 电子邮件分类', 19);
INSERT INTO `category` VALUES ('C3F9FAAF4EA64857ACFAB0D9C8D0E446', 'PHP', '1', 'PHP分类', 14);
INSERT INTO `category` VALUES ('C4DD8CA232864B31A367EE135D86382C', '计算机初级入门', '2', '计算机初级入门分类', 17);
INSERT INTO `category` VALUES ('C8E274EE5C99499080A98E24F0BD2E03', '.NET', '1', '.NET分类', 15);
INSERT INTO `category` VALUES ('D45D96DA359A4FEAB3AB4DCF2157FC06', 'JSP', '1', 'JSP分类', 11);
INSERT INTO `category` VALUES ('F5C091B3967442A2B35EFEFC4EF8746F', '微软Office', '2', '微软Office分类', 16);

SET FOREIGN_KEY_CHECKS = 1;
