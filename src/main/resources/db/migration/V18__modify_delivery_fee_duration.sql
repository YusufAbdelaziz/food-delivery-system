-- Turns out that using Time as a data type for storing estimated delivery duration would lead to
-- complications when summing durations. So storing it in seconds and converting it to Duration seems
-- to be a good choice.
ALTER TABLE
  `delivery_fee`
ADD
  COLUMN `estimated_duration_seconds` INT UNSIGNED NOT NULL DEFAULT 0;

UPDATE
  `delivery_fee`
SET
  `estimated_duration_seconds` = TIME_TO_SEC(`estimated_duration`);

ALTER TABLE
  `delivery_fee` DROP COLUMN `estimated_duration`;