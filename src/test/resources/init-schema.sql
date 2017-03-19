DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`       INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`     VARCHAR(64)      NOT NULL DEFAULT '',
  `password` VARCHAR(128)     NOT NULL DEFAULT '',
  `salt`     VARCHAR(32)      NOT NULL DEFAULT '',
  `head_url` VARCHAR(256)     NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id`            INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title`         VARCHAR(128)     NOT NULL DEFAULT '',
  `link`          VARCHAR(256)     NOT NULL DEFAULT '',
  `image`         VARCHAR(256)     NOT NULL DEFAULT '',
  `like_count`    INT              NOT NULL,
  `comment_count` INT              NOT NULL,
  `created_date`  DATETIME         NOT NULL,
  `user_id`       INT(11)          NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `login_ticket`;
CREATE TABLE `login_ticket` (
  `id`      INT         NOT NULL AUTO_INCREMENT,
  `user_id` INT         NOT NULL,
  `ticket`  VARCHAR(45) NOT NULL,
  `expired` DATETIME    NOT NULL,
  `status`  INT         NULL     DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `ticket_UNIQUE` (`ticket` ASC)
);