-- The admin may add the restaurant info and then later fill up the menu (maybe via a data entry guy)
ALTER TABLE
  `restaurant`
ADD
  COLUMN `visible` BOOLEAN DEFAULT 1;