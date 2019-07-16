DROP SCHEMA IF EXISTS `tmontica` ;
-- -----------------------------------------------------
-- Schema tmontica
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tmontica` DEFAULT CHARACTER SET utf8 ;
USE `tmontica` ;


-- -----------------------------------------------------
-- Table `tmontica`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tmontica`.`user` ;



CREATE TABLE IF NOT EXISTS `tmontica`.`user` (
  `name` VARCHAR(45) NOT NULL,
  `id` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `birth_date` DATETIME NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `role` CHAR(10) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `point` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;




-- -----------------------------------------------------
-- Table `tmontica`.`menu`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tmontica`.`menu` ;



CREATE TABLE IF NOT EXISTS `tmontica`.`menu` (
  `name_eng` VARCHAR(45) NOT NULL,
  `product_price` INT NOT NULL,
  `category` VARCHAR(45) NOT NULL,
  `monthly_menu` TINYINT(1) NOT NULL,
  `usable` TINYINT(1) NOT NULL,
  `img` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NULL,
  `sell_price` INT NOT NULL,
  `discount_rate` INT NOT NULL DEFAULT 0,
  `created_date` DATETIME NOT NULL,
  `updated_date` DATETIME NULL,
  `creator_id` VARCHAR(45) NOT NULL,
  `updator_id` VARCHAR(45) NULL,
  `stock` INT NOT NULL,
  `name_ko` VARCHAR(45) NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;




-- -----------------------------------------------------
-- Table `tmontica`.`cart_menu`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tmontica`.`cart_menu` ;



CREATE TABLE IF NOT EXISTS `tmontica`.`cart_menu` (
  `quantity` INT NOT NULL,
  `option` VARCHAR(255) NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NOT NULL,
  `option_price` INT NOT NULL,
  `menu_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Cart_item_User`
    FOREIGN KEY (`user_id`)
    REFERENCES `tmontica`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cart_menu_menu1`
    FOREIGN KEY (`menu_id`)
    REFERENCES `tmontica`.`menu` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;





-- -----------------------------------------------------
-- Table `tmontica`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tmontica`.`order` ;



CREATE TABLE IF NOT EXISTS `tmontica`.`order` (
  `id` INT NOT NULL,
  `order_date` DATETIME NOT NULL,
  `payment` VARCHAR(45) NOT NULL,
  `total_price` INT NOT NULL,
  `used_point` INT NULL,
  `real_price` INT NOT NULL,
  `status` CHAR(10) NOT NULL,
  `user_id` VARCHAR(45) NOT NULL,
  `user_agent` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_order_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `tmontica`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;





-- -----------------------------------------------------
-- Table `tmontica`.`option`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tmontica`.`option` ;



CREATE TABLE IF NOT EXISTS `tmontica`.`option` (
  `name` VARCHAR(45) NOT NULL,
  `price` INT NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;




-- -----------------------------------------------------
-- Table `tmontica`.`order_detail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tmontica`.`order_detail` ;



CREATE TABLE IF NOT EXISTS `tmontica`.`order_detail` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `option` VARCHAR(255) NULL,
  `price` INT NOT NULL,
  `quantity` INT NOT NULL,
  `menu_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_order_detail_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `tmontica`.`order` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_order_detail_menu1`
    FOREIGN KEY (`menu_id`)
    REFERENCES `tmontica`.`menu` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;




-- -----------------------------------------------------
-- Table `tmontica`.`point`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tmontica`.`point` ;



CREATE TABLE IF NOT EXISTS `tmontica`.`point` (
  `user_id` VARCHAR(45) NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` CHAR(10) NOT NULL,
  `date` DATETIME NOT NULL,
  `amount` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_point_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `tmontica`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;




-- -----------------------------------------------------
-- Table `tmontica`.`menu_option`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tmontica`.`menu_option` ;



CREATE TABLE IF NOT EXISTS `tmontica`.`menu_option` (
  `menu_id` INT NOT NULL,
  `option_id` INT NOT NULL,
  PRIMARY KEY (`menu_id`, `option_id`),
  CONSTRAINT `fk_menu_has_option_menu2`
    FOREIGN KEY (`menu_id`)
    REFERENCES `tmontica`.`menu` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_menu_has_option_option2`
    FOREIGN KEY (`option_id`)
    REFERENCES `tmontica`.`option` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;





-- -----------------------------------------------------
-- Table `tmontica`.`order_status_log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tmontica`.`order_status_log` ;



CREATE TABLE IF NOT EXISTS `tmontica`.`order_status_log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status_type` VARCHAR(45) NOT NULL,
  `editor_id` VARCHAR(45) NOT NULL,
  `order_id` INT NOT NULL,
  `modified_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_order_status_log_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `tmontica`.`order` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;





-- -----------------------------------------------------
-- Table `tmontica`.`banner`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tmontica`.`banner` ;



CREATE TABLE IF NOT EXISTS `tmontica`.`banner` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `use_page` VARCHAR(255) NULL,
  `usable` TINYINT(1) NOT NULL,
  `img` VARCHAR(255) NOT NULL,
  `link` VARCHAR(255) NOT NULL,
  `start_date` DATETIME NULL,
  `end_date` DATETIME NULL,
  `creator_id` VARCHAR(45) NOT NULL,
  `number` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

