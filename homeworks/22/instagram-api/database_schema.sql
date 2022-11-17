-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema instagram-api
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema instagram-api
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `instagram-api` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `instagram-api` ;

-- -----------------------------------------------------
-- Table `instagram-api`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instagram-api`.`users` (
  `id` VARCHAR(36) NOT NULL,
  `firstName` VARCHAR(30) NOT NULL,
  `lastName` VARCHAR(30) NOT NULL,
  `username` VARCHAR(16) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC) VISIBLE,
  UNIQUE INDEX `username` (`username` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `instagram-api`.`posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instagram-api`.`posts` (
  `id` VARCHAR(36) NOT NULL,
  `accountId` VARCHAR(36) NOT NULL,
  `link` VARCHAR(200) NOT NULL,
  `likes` INT NOT NULL DEFAULT '0',
  `description` VARCHAR(500) NULL DEFAULT NULL,
  `createdAt` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC) VISIBLE,
  INDEX `userId` (`accountId` ASC) VISIBLE,
  CONSTRAINT `posts_ibfk_1`
    FOREIGN KEY (`accountId`)
    REFERENCES `instagram-api`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `instagram-api`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instagram-api`.`comments` (
  `id` VARCHAR(36) NOT NULL,
  `accountId` VARCHAR(36) NOT NULL,
  `postId` VARCHAR(36) NOT NULL,
  `comment` VARCHAR(500) NOT NULL,
  `createdAt` TIMESTAMP NULL DEFAULT NULL,
  `editedAt` TIMESTAMP NULL DEFAULT NULL,
  `username` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC) VISIBLE,
  INDEX `userId` (`accountId` ASC) VISIBLE,
  INDEX `postId` (`postId` ASC) VISIBLE,
  CONSTRAINT `comments_ibfk_1`
    FOREIGN KEY (`accountId`)
    REFERENCES `instagram-api`.`users` (`id`),
  CONSTRAINT `comments_ibfk_2`
    FOREIGN KEY (`postId`)
    REFERENCES `instagram-api`.`posts` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `instagram-api`.`likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `instagram-api`.`likes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `accountId` VARCHAR(36) NOT NULL,
  `postId` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `userId` (`accountId` ASC) VISIBLE,
  INDEX `postId` (`postId` ASC) VISIBLE,
  CONSTRAINT `likes_ibfk_1`
    FOREIGN KEY (`accountId`)
    REFERENCES `instagram-api`.`users` (`id`),
  CONSTRAINT `likes_ibfk_2`
    FOREIGN KEY (`postId`)
    REFERENCES `instagram-api`.`posts` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
