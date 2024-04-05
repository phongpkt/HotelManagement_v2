CREATE TABLE IF NOT EXISTS hotel (
    hotel_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    phone_number VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS room_type (
    type_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    price_per_night DECIMAL(10, 2),
    capacity INT
);

CREATE TABLE IF NOT EXISTS room (
    room_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    hotel_id BIGINT NOT NULL,
    type_id BIGINT NOT NULL,
    status VARCHAR(255),
    FOREIGN KEY (hotel_id) REFERENCES Hotel(hotel_id),
    FOREIGN KEY (type_id) REFERENCES RoomType(type_id)
);

CREATE TABLE IF NOT EXISTS staff (
    staff_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    hotel_id BIGINT NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255),
    password VARCHAR(255),
    FOREIGN KEY (hotel_id) REFERENCES Hotel(hotel_id)
);

CREATE TABLE IF NOT EXISTS booking (
    booking_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    room_id BIGINT NOT NULL,
    check_in_date DATE,
    check_out_date DATE,
    total_price DECIMAL(10, 2),
    FOREIGN KEY (room_id) REFERENCES Room(room_id)
);

CREATE TABLE IF NOT EXISTS Payment (
    payment_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    total_amount DECIMAL(10, 2),
    payment_date DATE,
    payment_method VARCHAR(255),
    FOREIGN KEY (booking_id) REFERENCES Booking(booking_id)
);