CREATE DATABASE weather;

CREATE TABLE `weather`.`weather_details` (
  `city` VARCHAR(20) NOT NULL,
  `temperature` INT NULL,
  `humidity` INT NULL,
  `weather_type` VARCHAR(20) NULL,
  PRIMARY KEY (`city`))
ENGINE = InnoDB;

INSERT INTO `weather`.`weather_details`
(`city`,
`temperature`,
`humidity`,
`weather_type`)
VALUES
("Indore",
25,
70,
"Sunny");
