-- Forgot to add auto increment.
-- Remove order_item_id from order spec first
ALTER TABLE
  `order_spec` DROP FOREIGN KEY `order_spec_ibfk_1`;

ALTER TABLE
  `order_item` DROP PRIMARY KEY;

ALTER TABLE
  `order_item`
MODIFY
  COLUMN `order_item_id` bigint unsigned AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE
  `order_spec`
ADD
  CONSTRAINT `order_spec_ibfk_1` FOREIGN KEY (`order_item_id`) REFERENCES `order_item` (`order_item_id`);