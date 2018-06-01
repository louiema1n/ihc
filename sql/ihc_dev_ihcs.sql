DROP TABLE IF EXISTS `ihcs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ihcs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(255) NOT NULL COMMENT '病理号',
  `son` int(3) NOT NULL COMMENT '小号',
  `total` int(11) NOT NULL COMMENT '细项数',
  `item` varchar(2550) DEFAULT NULL COMMENT '具体西项',
  `time` datetime NOT NULL COMMENT '加做时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `state` tinyint(1) DEFAULT '1' COMMENT '状态',
  `userid` int(11) DEFAULT NULL COMMENT '加做人',
  `confirm` varchar(255) DEFAULT NULL COMMENT '确认人',
  `prj` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `results` varchar(15000) DEFAULT NULL COMMENT '原诊断',
  `ismatch` tinyint(1) DEFAULT '1' COMMENT '是否匹配到',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
