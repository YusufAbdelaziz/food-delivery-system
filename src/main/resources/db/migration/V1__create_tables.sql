CREATE TABLE `role` (
  `role_id` bigint unsigned AUTO_INCREMENT,
  `type` ENUM('USER', 'ADMIN', 'COURIER') NOT NULL UNIQUE,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`)
);

CREATE TABLE `region` (
  `region_id` bigint unsigned AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Region_ID`)
);

CREATE TABLE `delivery_fee` (
  `delivery_fee_id` bigint unsigned AUTO_INCREMENT,
  `to_region_id` bigint unsigned NOT NULL,
  `from_region_id` bigint unsigned NOT NULL,
  `price` decimal(5, 2) NOT NULL,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`delivery_fee_id`),
  FOREIGN KEY (`to_region_id`) REFERENCES region(region_id),
  FOREIGN KEY (`from_region_id`) REFERENCES region(region_id)
);

CREATE TABLE `courier` (
  `courier_id` bigint unsigned AUTO_INCREMENT,
  `phone_number` varchar(20) NOT NULL UNIQUE,
  `password` varchar(256) NOT NULL,
  `name` varchar(100) NOT NULL,
  `successful_orders` int unsigned DEFAULT 0,
  `active` BOOLEAN DEFAULT 0,
  `earnings` decimal(10, 2) DEFAULT 0.0,
  `role_id` bigint unsigned NOT NULL,
  `avg_rating` decimal(3, 2) DEFAULT 0.00,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`courier_id`),
  FOREIGN KEY (`role_id`) REFERENCES role(role_ID)
);

CREATE TABLE `user` (
  `user_id` bigint unsigned AUTO_INCREMENT,
  `phone_number` varchar(20) NOT NULL UNIQUE,
  `password` varchar(256),
  `email` varchar(50) UNIQUE,
  `name` varchar(100),
  `image_url` text NULL,
  `role_id` bigint unsigned NOT NULL,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  FOREIGN KEY (`role_id`) REFERENCES role(`role_id`)
);

CREATE TABLE `restaurant` (
  `restaurant_id` bigint unsigned AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `successful_orders` int unsigned DEFAULT 0,
  `avg_rating` decimal(3, 2) DEFAULT 0.00,
  `image_url` text NULL,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`restaurant_id`)
);

CREATE TABLE `address` (
  `address_id` bigint unsigned AUTO_INCREMENT,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `description` Text,
  `user_id` bigint unsigned NULL,
  `restaurant_id` bigint unsigned NULL,
  `region_id` bigint unsigned NOT NULL,
  `building_number` SMALLINT UNSIGNED NOT NULL,
  `apartment_number` SMALLINT UNSIGNED NOT NULL,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` BOOLEAN DEFAULT 0,
  PRIMARY KEY (`address_ID`),
  FOREIGN KEY (`user_id`) REFERENCES user(`user_id`),
  FOREIGN KEY (`restaurant_id`) REFERENCES restaurant(`restaurant_id`),
  FOREIGN KEY (`region_id`) REFERENCES region(`region_id`),
  CHECK (
    (
      user_id IS NOT NULL
      AND restaurant_id IS NULL
    )
    OR (
      user_id IS NULL
      AND restaurant_id IS NOT NULL
    )
  )
);

CREATE TABLE `cuisine` (
  `cuisine_id` bigint unsigned AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `image_url` text NULL,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`cuisine_id`)
);

CREATE TABLE `restaurant_cuisine` (
  `restaurant_id` bigint unsigned,
  `cuisine_id` bigint unsigned,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`restaurant_id`, `cuisine_id`),
  FOREIGN KEY (`cuisine_id`) REFERENCES cuisine(`cuisine_id`),
  FOREIGN KEY (`restaurant_id`) REFERENCES restaurant(`restaurant_id`)
);

CREATE TABLE `menu` (
  `menu_id` bigint unsigned AUTO_INCREMENT,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`menu_id`)
);

CREATE TABLE `section` (
  `section_id` bigint unsigned AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `menu_id` bigint unsigned,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`section_id`),
  FOREIGN KEY (`menu_id`) REFERENCES menu(`menu_id`)
);

CREATE TABLE `item` (
  `item_id` bigint unsigned AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `price` decimal(6, 2) NOT NULL,
  `section_id` bigint unsigned,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`item_id`),
  FOREIGN KEY (`section_id`) REFERENCES section(`section_id`)
);

CREATE TABLE `spec` (
  `spec_id` bigint unsigned AUTO_INCREMENT,
  `type` ENUM('CHECKBOX', 'RADIO') NOT NULL,
  `name` varchar(20) NOT NULL,
  `item_id` bigint unsigned,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`spec_id`),
  FOREIGN KEY (`item_id`) REFERENCES item(`item_id`)
);

CREATE TABLE `spec_option` (
  `spec_option_id` bigint unsigned PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `price` decimal(6, 2) NOT NULL,
  `spec_id` bigint unsigned,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`spec_id`) REFERENCES spec(`spec_id`)
);

CREATE TABLE `promotion` (
  `promotion_id` bigint unsigned AUTO_INCREMENT,
  `restaurant_id` bigint unsigned null,
  `headline` VARCHAR(250),
  `description` TEXT,
  `discount_type` ENUM('FIXED', 'PERCENTAGE', 'DELIVERY') NOT NULL,
  `discount_value` decimal(7, 2) NOT NULL,
  `code` varchar(100),
  `start_Date` timestamp,
  `end_Date` timestamp null,
  `max_users` int unsigned null,
  `used_count` int unsigned DEFAULT 0,
  `active` BOOLEAN,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`promotion_id`),
  FOREIGN KEY (`restaurant_id`) REFERENCES restaurant(`restaurant_id`)
);

CREATE TABLE `user_promotion` (
  `user_promotion_id` bigint unsigned AUTO_INCREMENT,
  `user_id` bigint unsigned,
  `promotion_id` bigint unsigned,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_promotion_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`),
  FOREIGN KEY (`promotion_id`) REFERENCES promotion(`promotion_id`)
);

CREATE TABLE `order` (
  `order_id` bigint unsigned AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL,
  `courier_id` bigint unsigned NOT NULL,
  `promotion_id` bigint unsigned NULL,
  `estimated_delivery_date` timestamp NOT NULL,
  `status` Enum(
    'PENDING',
    'PREPARING',
    'DISPATCHED',
    'SUCCESSFUL',
    'CANCELED'
  ) DEFAULT 'PENDING',
  `order_rating` tinyint unsigned,
  `courier_rating` tinyint unsigned,
  `restaurant_feedback` varchar(2000) null,
  `courier_feedback` varchar(2000) null,
  `order_total` decimal(10, 2) NOT NULL,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`),
  FOREIGN KEY (`courier_id`) REFERENCES courier(`courier_id`),
  FOREIGN KEY (`promotion_id`) REFERENCES promotion(`promotion_id`)
);

CREATE TABLE `order_restaurant` (
  `order_restaurant_id` bigint unsigned AUTO_INCREMENT,
  `order_id` bigint unsigned,
  `restaurant_id` bigint unsigned,
  `delivery_fee_id` bigint unsigned,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_restaurant_id`),
  FOREIGN KEY (`order_id`) REFERENCES `order`(`order_id`),
  FOREIGN KEY (`delivery_fee_id`) REFERENCES `delivery_fee`(`delivery_fee_id`),
  FOREIGN KEY (`restaurant_id`) REFERENCES restaurant(`restaurant_id`)
);

CREATE TABLE `order_item` (
  `order_item_id` bigint unsigned,
  `order_restaurant_id` bigint unsigned,
  `name` varchar(20) NOT NULL,
  `price` decimal(6, 2) NOT NULL,
  `quantity` int NOT NULL,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_item_id`),
  FOREIGN KEY (`order_restaurant_id`) REFERENCES order_restaurant(`order_restaurant_id`)
);

CREATE TABLE `order_spec` (
  `order_spec_id` bigint unsigned AUTO_INCREMENT,
  `order_item_id` bigint unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_spec_id`),
  FOREIGN KEY (`order_item_id`) REFERENCES order_item(`order_item_id`)
);

CREATE TABLE `order_option` (
  `order_option_id` bigint unsigned AUTO_INCREMENT,
  `order_spec_id` bigint unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `price` decimal(6, 2) NOT NULL,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_option_id`),
  FOREIGN KEY (`order_spec_id`) REFERENCES order_spec(`order_spec_id`)
);