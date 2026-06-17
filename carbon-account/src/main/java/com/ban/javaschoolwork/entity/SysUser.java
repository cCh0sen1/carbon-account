CREATE TABLE `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `nickname` VARCHAR(50),
    `avatar` VARCHAR(255),
    `total_points` INT DEFAULT 0,
    `total_carbon` DOUBLE DEFAULT 0.0,
    `role` VARCHAR(20) DEFAULT 'USER',
    `deleted` TINYINT DEFAULT 0,
    `create_time` DATETIME,
    `update_time` DATETIME,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;