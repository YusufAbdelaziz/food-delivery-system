-- Since delivery fees may change over time, we need to hold the current delivery fee.
ALTER TABLE
  `order_delivery_fee`
ADD
  COLUMN `price` decimal(5, 2) NOT NULL;