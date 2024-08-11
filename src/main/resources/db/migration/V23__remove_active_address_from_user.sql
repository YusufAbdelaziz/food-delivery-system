-- Turns out that adding active address id to the user adds unnecessary complexity.
ALTER TABLE
  `user` DROP FOREIGN KEY `fk_user_active_address`;

ALTER TABLE
  `user` DROP COLUMN `active_address_id`;