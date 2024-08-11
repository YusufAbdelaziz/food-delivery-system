-- Unnecessary column.
ALTER TABLE
  `order_restaurant` DROP CONSTRAINT `fk_order_delivery_fee_id`;

ALTER TABLE
  `order_restaurant` DROP COLUMN `order_delivery_fee_id`;