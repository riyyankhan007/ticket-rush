ALTER TABLE theatres
ADD COLUMN owner_id BIGINT;

ALTER TABLE theatres
ADD CONSTRAINT fk_theatre_owner
FOREIGN KEY (owner_id)
REFERENCES users(id);