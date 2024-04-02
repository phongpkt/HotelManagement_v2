CREATE TABLE IF NOT EXISTS Hotel (
    hotel_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    phone_number VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS RoomType (
    type_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    pricePerNight DECIMAL(10, 2),
    capacity INT
);

CREATE TABLE IF NOT EXISTS Room (
    room_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    hotel_id BIGINT NOT NULL,
    type_id BIGINT NOT NULL,
    status VARCHAR(255),
    FOREIGN KEY (hotel_id) REFERENCES Hotel(hotel_id),
    FOREIGN KEY (type_id) REFERENCES RoomType(type_id)
);

CREATE TABLE IF NOT EXISTS Staff (
    staff_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    hotel_id BIGINT NOT NULL,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255),
    password VARCHAR(255),
    FOREIGN KEY (hotel_id) REFERENCES Hotel(hotel_id)
);

CREATE TABLE IF NOT EXISTS Booking (
    booking_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    room_id BIGINT NOT NULL,
    check_in_date DATE,
    check_out_date DATE,
    totalPrice DECIMAL(10, 2),
    FOREIGN KEY (room_id) REFERENCES Room(room_id)
);
