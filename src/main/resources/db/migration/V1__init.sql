CREATE TABLE credentials (
  id            INT AUTO_INCREMENT NOT NULL,
  username      VARCHAR (255) NOT NULL,
  password      VARCHAR (255) NOT NULL,
  token         VARCHAR (255) NOT NULL,
  created_at    TIMESTAMP     NOT NULL
);

ALTER TABLE credentials
  ADD CONSTRAINT credentials_id PRIMARY KEY (id);
