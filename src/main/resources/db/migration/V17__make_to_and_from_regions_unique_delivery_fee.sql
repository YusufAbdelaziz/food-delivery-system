ALTER TABLE
  `delivery_fee`
ADD
  CONSTRAINT `unique_region_pair` UNIQUE (`to_region_id`, `from_region_id`);