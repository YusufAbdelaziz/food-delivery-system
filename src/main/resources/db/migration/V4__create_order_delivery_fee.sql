-- Since a user can have multiple restaurants, this means that there'll be multiple fees
-- for ordering from these restaurants.
CREATE TABLE `order_delivery_fee` (
  `order_delivery_fee_id` bigint unsigned AUTO_INCREMENT PRIMARY KEY,
  `order_id` bigint unsigned NOT NULL,
  `delivery_fee_id` bigint unsigned NOT NULL,
  `created_by` varchar(100),
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100),
  `last_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`order_id`) REFERENCES `order`(`order_id`),
  FOREIGN KEY (`delivery_fee_id`) REFERENCES `delivery_fee`(`delivery_fee_id`)
);