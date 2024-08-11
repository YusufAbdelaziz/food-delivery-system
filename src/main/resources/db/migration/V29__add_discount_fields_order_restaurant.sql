ALTER TABLE
  `order_restaurant`
ADD
  COLUMN `total_restaurant_receipt` decimal(10, 2) NOT NULL,
ADD
  COLUMN `discounted_total_restaurant_receipt` decimal(10, 2) NULL;