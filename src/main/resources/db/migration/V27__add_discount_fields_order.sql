ALTER TABLE
  `order`
ADD
  COLUMN `total_delivery_fees` decimal(10, 2) NOT NULL,
ADD
  COLUMN `discounted_total_delivery_fees` decimal(10, 2) NULL,
ADD
  COLUMN `discounted_order_total` decimal(10, 2) NULL;