CREATE TABLE `users` (
                         `user_id` bigint NOT NULL AUTO_INCREMENT,
                         `active` bit(1) NOT NULL,
                         `email` varchar(255) NOT NULL,
                         `name` varchar(30) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         PRIMARY KEY (`user_id`),
                         UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
                         UNIQUE KEY `UK_r53o2ojjw4fikudfnsuuga336` (`password`)
);

CREATE TABLE `devices` (
                           `device_id` bigint NOT NULL AUTO_INCREMENT,
                           `device_unique_id` binary(255) NOT NULL,
                           `meta_tag` varchar(255) NOT NULL,
                           `os` varchar(255) NOT NULL,
                           `type` varchar(255) NOT NULL,
                           `user_user_id` bigint DEFAULT NULL,
                           PRIMARY KEY (`device_id`),
                           UNIQUE KEY `UK_1s0opyqfcmq26q37aalrdhl2j` (`device_unique_id`),
                           KEY `FKethutmm44xaa380hdw029auft` (`user_user_id`),
                           CONSTRAINT `FKethutmm44xaa380hdw029auft` FOREIGN KEY (`user_user_id`) REFERENCES `users` (`user_id`)
);

CREATE TABLE `e_sims` (
                          `e_sim_id` bigint NOT NULL AUTO_INCREMENT,
                          `iccid` varchar(255) NOT NULL,
                          `imsi` varchar(255) NOT NULL,
                          `activation_code` varchar(255) NOT NULL,
                          `e_id` binary(255) NOT NULL,
                          `device_device_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`e_sim_id`),
                          UNIQUE KEY `UK_opvqepe8k64n1oh9sp0oulmaa` (`e_id`),
                          KEY `FK7xnhl0ju2xw6y1e7p6ryhupbq` (`device_device_id`),
                          CONSTRAINT `FK7xnhl0ju2xw6y1e7p6ryhupbq` FOREIGN KEY (`device_device_id`) REFERENCES `devices` (`device_id`)
);