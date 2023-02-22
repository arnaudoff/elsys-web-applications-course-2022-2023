-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema instagram
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema instagram
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `instagram` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `instagram` ;

-- -----------------------------------------------------
-- Table `instagram`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instagram`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NULL DEFAULT NULL,
  `email` VARCHAR(20) NULL DEFAULT NULL,
  `password` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `instagram`.`pictures`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instagram`.`pictures` (
  `id` INT NOT NULL,
  `description` VARCHAR(100) NULL DEFAULT NULL,
  `picture` VARCHAR(200) NOT NULL,
  `picture_id` INT NOT NULL AUTO_INCREMENT,
  UNIQUE INDEX `picture_id` (`picture_id` ASC) VISIBLE,
  INDEX `id` (`id` ASC) VISIBLE,
  CONSTRAINT `pictures_ibfk_1`
    FOREIGN KEY (`id`)
    REFERENCES `instagram`.`users` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `instagram`.`coments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instagram`.`coments` (
  `id` INT NOT NULL,
  `coment` VARCHAR(200) NULL DEFAULT NULL,
  `coment_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`coment_id`),
  INDEX `id` (`id` ASC) VISIBLE,
  CONSTRAINT `coments_ibfk_1`
    FOREIGN KEY (`id`)
    REFERENCES `instagram`.`pictures` (`picture_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `instagram`.`likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instagram`.`likes` (
  `person_id` VARCHAR(20) NULL DEFAULT NULL,
  `post_id` INT NULL DEFAULT NULL,
  `likes` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`likes`),
  UNIQUE INDEX `likes_pk` (`post_id` ASC, `person_id` ASC) VISIBLE,
  CONSTRAINT `likes_ibfk_1`
    FOREIGN KEY (`post_id`)
    REFERENCES `instagram`.`pictures` (`picture_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
