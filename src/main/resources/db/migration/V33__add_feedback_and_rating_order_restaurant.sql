ALTER TABLE
  `order_restaurant`
ADD
  COLUMN `restaurant_feedback` varchar(2000) null,
ADD
  COLUMN `restaurant_rating` tinyint unsigned;