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
  `first_name` VARCHAR(20) NOT NULL,
  `second_name` VARCHAR(20) NOT NULL,
  `username` VARCHAR(64) NOT NULL,
  `password` INT NOT NULL,
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `users_pk` (`username` ASC) VISIBLE,
  UNIQUE INDEX `users_pkdafs` (`username` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `instagram`.`posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instagram`.`posts` (
  `user` VARCHAR(64) NOT NULL,
  `pic_url` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  INDEX `foreign_key_name` (`user` ASC) VISIBLE,
  CONSTRAINT `foreign_key_name`
    FOREIGN KEY (`user`)
    REFERENCES `instagram`.`users` (`username`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `instagram`.`coments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instagram`.`coments` (
  `text` VARCHAR(255) NOT NULL,
  `post_id` INT UNSIGNED NOT NULL,
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(64) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `coments_posts_null_fk` (`post_id` ASC) VISIBLE,
  CONSTRAINT `coments_posts_null_fk`
    FOREIGN KEY (`post_id`)
    REFERENCES `instagram`.`posts` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `instagram`.`likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instagram`.`likes` (
  `post_id` INT UNSIGNED NULL DEFAULT NULL,
  `username` VARCHAR(64) NULL DEFAULT NULL,
  UNIQUE INDEX `likes_pk` (`post_id` ASC, `username` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
