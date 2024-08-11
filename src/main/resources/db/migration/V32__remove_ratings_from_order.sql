-- Since an order may contain multiple restaurants, a restaurant's feedback should belong to OrderRestaurant.
ALTER TABLE
  `order` DROP COLUMN `order_rating`,
  DROP COLUMN `restaurant_feedback`;