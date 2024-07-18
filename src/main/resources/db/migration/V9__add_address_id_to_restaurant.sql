ALTER TABLE
  `restaurant`
ADD
  `address_id` bigint unsigned NOT NULL,
ADD
  CONSTRAINT `fk_address_id` FOREIGN KEY (`address_id`) REFERENCES address(`address_id`);