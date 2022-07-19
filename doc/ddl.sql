CREATE TABLE `tb_label` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `code` varchar(64) NOT NULL COMMENT '标签代码',
  `name` varchar(128) NOT NULL COMMENT '标签名',
  `dataType` int NOT NULL DEFAULT '0' COMMENT '数据类型',
  `config` text COMMENT '标签定义配置，例如样式',
  `created` int NOT NULL DEFAULT '0' COMMENT '创建时间',
  `updated` int NOT NULL DEFAULT '0' COMMENT '更新时间',
  `deleted` int NOT NULL DEFAULT '0' COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `mitosis`.`tb_template` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL COMMENT '模版标题',
  `content` longtext COMMENT '模版内容',
  `created` int NOT NULL DEFAULT '0' COMMENT '创建时间',
  `updated` int NOT NULL DEFAULT '0' COMMENT '更新时间',
  `deleted` int NOT NULL DEFAULT '0' COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `idx_title` (`title`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `tb_template_label` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `templateId` int(11) NOT NULL COMMENT '模版ID',
  `labelId` int(11) NOT NULL COMMENT '标签ID',
  `code` varchar(64) DEFAULT NULL COMMENT '标签code',
  `created` int(11) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `updated` int(11) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;