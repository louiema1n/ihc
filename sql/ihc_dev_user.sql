DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `nick` varchar(255) DEFAULT NULL COMMENT '昵称',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `remark` varchar(2550) DEFAULT NULL COMMENT '备注',
  `state` tinyint(1) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户信息表';
