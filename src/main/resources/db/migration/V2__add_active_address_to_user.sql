-- Alter the User table to add the active_address_id column
ALTER TABLE
  `user`
ADD
  COLUMN `active_address_id` bigint unsigned NULL;

-- Add the foreign key constraint to the active_address_id column
ALTER TABLE
  `user`
ADD
  CONSTRAINT `fk_user_active_address` FOREIGN KEY (`active_address_id`) REFERENCES `address`(`address_id`);