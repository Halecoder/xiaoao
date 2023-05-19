建表语句

```sql
/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : xiaoao

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 19/05/2023 11:50:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mall_user
-- ----------------------------
DROP TABLE IF EXISTS `mall_user`;
CREATE TABLE `mall_user`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `wallet_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `bank_card_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mall_user
-- ----------------------------
INSERT INTO `mall_user` VALUES (3, 'hanlei', 'r5yDkTfNR2V/2mtV+nzcnw==', 'b844f5d257964d0fad6c4c8171bc7b50', '1234567890');

-- ----------------------------
-- Table structure for mall_wallet
-- ----------------------------
DROP TABLE IF EXISTS `mall_wallet`;
CREATE TABLE `mall_wallet`  (
  `wallet_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` int NULL DEFAULT NULL,
  `balance` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`wallet_id`) USING BTREE,
  INDEX `mall_wallet_ibfk_1`(`user_id` ASC) USING BTREE,
  CONSTRAINT `mall_wallet_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `mall_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mall_wallet
-- ----------------------------
INSERT INTO `mall_wallet` VALUES ('b844f5d257964d0fad6c4c8171bc7b50', 3, 110.00);

-- ----------------------------
-- Table structure for wallet_transaction
-- ----------------------------
DROP TABLE IF EXISTS `wallet_transaction`;
CREATE TABLE `wallet_transaction`  (
  `transaction_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` int NULL DEFAULT NULL,
  `transaction_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `amount` decimal(10, 2) NULL DEFAULT NULL,
  `transaction_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`transaction_id`) USING BTREE,
  INDEX `wallet_transaction_ibfk_1`(`user_id` ASC) USING BTREE,
  CONSTRAINT `wallet_transaction_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `mall_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wallet_transaction
-- ----------------------------
INSERT INTO `wallet_transaction` VALUES ('2d77e88ef2e042e3b2fb0d12c4b93628', 3, '消费', 10.00, '2023-05-19 11:24:16');
INSERT INTO `wallet_transaction` VALUES ('5ef46326d76d44349c06c0792e80181a', 3, '充值', 100.00, '2023-05-19 11:18:05');
INSERT INTO `wallet_transaction` VALUES ('ea83407e8b4243a4a927c503a3322728', 3, '充值', 100.00, '2023-05-19 11:17:12');
INSERT INTO `wallet_transaction` VALUES ('f02539578f6240caa03da1c97396bea1', 3, '充值', 20.00, '2023-05-19 11:23:55');
INSERT INTO `wallet_transaction` VALUES ('f18b519eb27049f48ea4de54962547ae', 3, '充值', 0.00, '2023-05-19 11:13:53');

SET FOREIGN_KEY_CHECKS = 1;

```

- Jwt拦截认证，进行统一登陆校验
- Javadoc+apifox生成零侵入式接口文档
- 使用开源工具hutool生成UUID、时间戳
- 全局异常统一处理，日志统一打印
- mybatis-generator自动生成代码
- 使用BigDecimal处理金额，避免精度丢失
- 使用事务处理转账业务，避免转账过程中出现异常导致数据不一致


接口文档地址：https://apifox.com/apidoc/shared-2b488bef-e222-4e16-8f77-7bb974cde440