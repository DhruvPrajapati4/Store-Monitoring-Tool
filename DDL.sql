CREATE DATABASE IF NOT EXISTS Store_Monitoring;
USE Store_Monitoring;

CREATE TABLE `storeStatus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `store_id` bigint DEFAULT NULL,
  `status` text,
  `timestamp_utc` text,
  PRIMARY KEY (`id`)
);

CREATE TABLE `businessHours` (
  `id` int NOT NULL AUTO_INCREMENT,
  `store_id` bigint NOT NULL,
  `day_of_week` int NOT NULL,
  `start_time_local` time NOT NULL,
  `end_time_local` time NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `storeTimezone` (
  `id` int NOT NULL AUTO_INCREMENT,
  `store_id` bigint NOT NULL,
  `timezone` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `report_id` varchar(255) NOT NULL,
  `csv_data` text NOT NULL,
  `status` enum('Running','Completed') NOT NULL DEFAULT 'Running',
  PRIMARY KEY (`id`)
);

