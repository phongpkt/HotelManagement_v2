CREATE TABLE IF NOT EXISTS guest (
    guest_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    date_of_birth DATE,
    address VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(20)
);

ALTER TABLE booking ADD COLUMN guest_id BIGINT;
ALTER TABLE booking ADD FOREIGN KEY (guest_id) REFERENCES Guest(guest_id);
