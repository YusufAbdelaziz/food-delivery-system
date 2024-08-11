-- Turns out the order restaurant should reference the order delivery fee not the delivery fee 
-- because the delivery fee may change in the future.
ALTER TABLE
  `order_restaurant` DROP CONSTRAINT `order_restaurant_ibfk_2`;

ALTER TABLE
  `order_restaurant` DROP COLUMN `delivery_fee_id`;

ALTER TABLE
  `order_restaurant`
ADD
  `order_delivery_fee_id` bigint unsigned,
ADD
  CONSTRAINT `fk_order_delivery_fee_id` FOREIGN KEY (`order_delivery_fee_id`) REFERENCES `order_delivery_fee`(`order_delivery_fee_id`);