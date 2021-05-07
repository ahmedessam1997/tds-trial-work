CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `status` varchar(10),
                         `email` varchar(255) NOT NULL,
                         `name` varchar(30) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
);

CREATE TABLE `devices` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `device_unique_id` binary(255),
                           `meta_tag` varchar(255) NOT NULL,
                           `os` varchar(255) NOT NULL,
                           `type` varchar(255) NOT NULL,
                           `user_id` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `UK_1s0opyqfcmq26q37aalrdhl2j` (`device_unique_id`),
                           KEY `FKrfbri1ymrwywdydc4dgywe1bt` (`user_id`),
                           CONSTRAINT `FKrfbri1ymrwywdydc4dgywe1bt` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `e_sims` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `iccid` varchar(255) NOT NULL,
                          `imsi` varchar(255) NOT NULL,
                          `activation_code` varchar(255) NOT NULL,
                          `e_id` binary(255),
                          `device_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `UK_opvqepe8k64n1oh9sp0oulmaa` (`e_id`),
                          KEY `FKp7okbbtu99t5b23pf92272w10` (`device_id`),
                          CONSTRAINT `FKp7okbbtu99t5b23pf92272w10` FOREIGN KEY (`device_id`) REFERENCES `devices` (`id`)
);