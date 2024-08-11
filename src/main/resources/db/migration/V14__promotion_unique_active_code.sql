-- This rule indicates that two codes can not be the same and active at the same time.
-- Note that you may have duplicate codes in the table
ALTER TABLE
  `promotion`
ADD
  CONSTRAINT unique_code_active UNIQUE(code, active);