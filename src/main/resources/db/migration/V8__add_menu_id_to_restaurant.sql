ALTER TABLE
  `restaurant`
ADD
  `menu_id` bigint unsigned,
ADD
  CONSTRAINT `fk_menu_id` FOREIGN KEY (`menu_id`) REFERENCES menu(`menu_id`);