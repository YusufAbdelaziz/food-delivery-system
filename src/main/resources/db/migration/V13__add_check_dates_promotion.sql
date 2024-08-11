ALTER TABLE
  `promotion`
ADD
  CONSTRAINT chk_end_after_start CHECK (`end_date` > `start_date`);