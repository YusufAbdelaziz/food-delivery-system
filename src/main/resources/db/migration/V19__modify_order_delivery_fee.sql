ALTER TABLE
  `order_delivery_fee` DROP CONSTRAINT `order_delivery_fee_ibfk_2`;

ALTER TABLE
  `order_delivery_fee` DROP COLUMN `delivery_fee_id`;

ALTER TABLE
  `order_delivery_fee`
ADD
  COLUMN `to_region_id` bigint unsigned NOT NULL,
ADD
  COLUMN `from_region_id` bigint unsigned NOT NULL,
ADD
  COLUMN `estimated_duration_seconds` INT UNSIGNED NOT NULL DEFAULT 0,
ADD
  CONSTRAINT `fk_order_delivery_fee_to_region` FOREIGN KEY (`to_region_id`) REFERENCES `region` (`region_id`),
ADD
  CONSTRAINT `fk_order_delivery_fee_from_region` FOREIGN KEY (`from_region_id`) REFERENCES `region` (`region_id`);