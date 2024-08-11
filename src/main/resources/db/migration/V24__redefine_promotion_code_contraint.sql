-- The constraint I added earlier doesn't cover up allowing duplicate inactive codes. Also MySQL doesn't support
-- partial indexing so a trigger is added whenever a promotion is inserted.
CREATE TRIGGER check_unique_active_promotion_code BEFORE
INSERT
  ON promotion FOR EACH ROW BEGIN IF NEW.active = TRUE THEN IF EXISTS (
    SELECT
      1
    FROM
      promotion
    WHERE
      code = NEW.code
      AND active = TRUE
  ) THEN SIGNAL SQLSTATE '45000'
SET
  MESSAGE_TEXT = 'Cannot insert: an active promotion with this code already exists';

END IF;

END IF;

END;

-- Drop the old constraint
ALTER TABLE
  `promotion` DROP CONSTRAINT `unique_code_active`;