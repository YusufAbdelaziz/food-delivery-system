CREATE TABLE Token (
  id bigint unsigned AUTO_INCREMENT PRIMARY KEY,
  token VARCHAR(1024) NOT NULL,
  token_hash CHAR(64) GENERATED ALWAYS AS (SHA2(token, 256)) STORED,
  token_type ENUM('BEARER') NOT NULL DEFAULT 'BEARER',
  revoked BOOLEAN NOT NULL DEFAULT FALSE,
  expired BOOLEAN NOT NULL DEFAULT FALSE,
  user_id bigint unsigned,
  courier_id bigint unsigned,
  UNIQUE KEY uk_token_hash (token_hash),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`),
  FOREIGN KEY (`courier_id`) REFERENCES `courier`(`courier_id`),
  CONSTRAINT chk_user_or_courier CHECK (
    (
      user_id IS NOT NULL
      AND courier_id IS NULL
    )
    OR (
      user_id IS NULL
      AND courier_id IS NOT NULL
    )
  )
);

-- Create index on user_id for faster joins
CREATE INDEX idx_token_user ON Token(user_id);

-- Create index on courier_id for faster joins
CREATE INDEX idx_token_courier ON Token(courier_id);