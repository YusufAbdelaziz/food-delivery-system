-- Address id is added to the restaurant table since it's the owning side of one-to-one relationship.
-- There's no point in keeping the restaurant id or the check constraint in address table.
ALTER TABLE
  `address` DROP CONSTRAINT `address_chk_1`;

ALTER TABLE
  `address` DROP CONSTRAINT `address_ibfk_2`;

ALTER TABLE
  `address` DROP COLUMN `restaurant_id`;