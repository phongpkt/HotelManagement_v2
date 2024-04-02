CREATE TABLE IF NOT EXISTS Payment (
    payment_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    totalAmount DECIMAL(10, 2),
    payment_date DATE,
    payment_method VARCHAR(255),
    FOREIGN KEY (booking_id) REFERENCES Booking(booking_id)
);