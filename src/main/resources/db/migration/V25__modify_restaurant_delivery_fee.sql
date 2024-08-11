-- Restaurant delivery fee references order id instead of an order restaurant.
ALTER TABLE
  `restaurant_delivery_fee`
ADD
  COLUMN `order_restaurant_id` bigint unsigned,
ADD
  CONSTRAINT fk_restaurant_delivery_fee_order_restaurant FOREIGN KEY (`order_restaurant_id`) REFERENCES `order_restaurant`(`order_restaurant_id`);