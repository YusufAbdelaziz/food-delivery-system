-- Delivery fee is associated with an order restaurant not the order itself.
RENAME TABLE `order_delivery_fee` TO `restaurant_delivery_fee`;

-- Rename the primary key column
ALTER TABLE
  `restaurant_delivery_fee` DROP PRIMARY KEY,
  CHANGE COLUMN `order_delivery_fee_id` `restaurant_delivery_fee_id` bigint unsigned NOT NULL AUTO_INCREMENT,
ADD
  PRIMARY KEY (`restaurant_delivery_fee_id`);

-- Add the new column order_restaurant_id (if it doesn't exist)
SET
  @exist := (
    SELECT
      COUNT(*)
    FROM
      INFORMATION_SCHEMA.COLUMNS
    WHERE
      TABLE_NAME = 'restaurant_delivery_fee'
      AND COLUMN_NAME = 'order_restaurant_id'
  );

SET
  @sqlstmt := IF(
    @exist = 0,
    'ALTER TABLE `restaurant_delivery_fee` 
     ADD COLUMN `order_restaurant_id` bigint unsigned NOT NULL AFTER `restaurant_delivery_fee_id`',
    'SELECT "Column order_restaurant_id already exists"'
  );

PREPARE stmt
FROM
  @sqlstmt;

EXECUTE stmt;

DEALLOCATE PREPARE stmt;

-- Check if order_id exists and copy data if it does
SET
  @exist := (
    SELECT
      COUNT(*)
    FROM
      INFORMATION_SCHEMA.COLUMNS
    WHERE
      TABLE_NAME = 'restaurant_delivery_fee'
      AND COLUMN_NAME = 'order_id'
  );

SET
  @sqlstmt := IF(
    @exist > 0,
    'UPDATE `restaurant_delivery_fee` SET `order_restaurant_id` = `order_id`',
    'SELECT "Column order_id does not exist, skipping data copy"'
  );

PREPARE stmt
FROM
  @sqlstmt;

EXECUTE stmt;

DEALLOCATE PREPARE stmt;

-- Remove the order_id column and its foreign key constraint if they exist
SET
  @fkconstraint := (
    SELECT
      CONSTRAINT_NAME
    FROM
      INFORMATION_SCHEMA.KEY_COLUMN_USAGE
    WHERE
      TABLE_NAME = 'restaurant_delivery_fee'
      AND COLUMN_NAME = 'order_id'
      AND CONSTRAINT_NAME != 'PRIMARY'
    LIMIT
      1
  );

SET
  @sqlstmt := IF(
    @fkconstraint IS NOT NULL,
    CONCAT(
      'ALTER TABLE `restaurant_delivery_fee` DROP FOREIGN KEY ',
      @fkconstraint
    ),
    'SELECT "No foreign key constraint found for order_id"'
  );

PREPARE stmt
FROM
  @sqlstmt;

EXECUTE stmt;

DEALLOCATE PREPARE stmt;

SET
  @exist := (
    SELECT
      COUNT(*)
    FROM
      INFORMATION_SCHEMA.COLUMNS
    WHERE
      TABLE_NAME = 'restaurant_delivery_fee'
      AND COLUMN_NAME = 'order_id'
  );

SET
  @sqlstmt := IF(
    @exist > 0,
    'ALTER TABLE `restaurant_delivery_fee` DROP COLUMN `order_id`, DROP INDEX `order_id`',
    'SELECT "Column order_id does not exist, skipping drop"'
  );

PREPARE stmt
FROM
  @sqlstmt;

EXECUTE stmt;

DEALLOCATE PREPARE stmt;

-- Add a new foreign key for order_restaurant_id
ALTER TABLE
  `restaurant_delivery_fee`
ADD
  CONSTRAINT `fk_restaurant_delivery_fee_order` FOREIGN KEY (`order_restaurant_id`) REFERENCES `order` (`order_id`);

-- Add an index for the new order_restaurant_id column
ALTER TABLE
  `restaurant_delivery_fee`
ADD
  INDEX `idx_order_restaurant_id` (`order_restaurant_id`);