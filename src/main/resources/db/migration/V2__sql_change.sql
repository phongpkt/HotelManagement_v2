ALTER TABLE booking ADD COLUMN guest_id BIGINT;
ALTER TABLE booking ADD FOREIGN KEY (guest_id) REFERENCES guest(guest_id);

ALTER TABLE booking ADD COLUMN status VARCHAR(255);

ALTER TABLE staff ADD COLUMN role varchar(255);
