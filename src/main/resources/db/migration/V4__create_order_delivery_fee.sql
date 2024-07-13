-- Since a user can have multiple restaurants, this means that there'll be multiple fees
-- for ordering from these restaurants.
CREATE TABLE `order_delivery_fee` (
  `order_delivery_fee` int AUTO_INCREMENT,
  `order_id` bigint unsigned NOT NULL,
  `delivery_fee_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`order_delivery_fee`),
  FOREIGN KEY (`order_id`) REFERENCES `order`(`order_id`),
  FOREIGN KEY (`delivery_fee_id`) REFERENCES `delivery_fee`(`delivery_fee_id`)
);