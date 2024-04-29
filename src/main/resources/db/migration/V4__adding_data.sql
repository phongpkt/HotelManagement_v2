INSERT INTO hotel (name, address, phone_number) VALUES
('Narcissus Hotel & Apartment', ' Số 33 ngõ Tức Mặc, phường Cửa Nam, quận Hoàn Kiếm, Hà Nội, Việt Nam', '0337248276'),
('Narcissus Hotel & Apartment', 'Số 84 Lê Duẩn, phường Văn Miếu, quận Hoàn Kiếm, Hà Nội, Việt Nam', '0337248276');

INSERT INTO room_type (name, description, price_per_night, capacity) VALUES
('Deluxe Room', 'Trải nghiệm sự sang trọng trong Phòng Deluxe rộng 40m2 của chúng tôi có bếp riêng, TV thông minh, hệ thống cách âm, minibar và Wi-Fi miễn phí!', 1350000.00, 2),
('Superior Room', 'Hãy trải nghiệm sự sang trọng trong Phòng Superior rộng 38m2 của chúng tôi với bếp riêng, TV Thông minh, hệ thống cách âm, minibar và Wi-Fi miễn phí!', 1050000.00, 2),
('Superior City Room', 'Khám phá sự thoải mái trong Phòng Superior Thành phố của chúng tôi có giường cỡ King, TV Thông minh, Wi-Fi miễn phí và tầm nhìn quyến rũ ra thành phố.', 832000.00, 2),
('Standard Room', 'Hãy tận hưởng sự thoải mái với Phòng Tiêu chuẩn của chúng tôi, có giường cỡ King, TV Thông minh, Wi-Fi miễn phí và môi trường không khói thuốc trên diện tích 25m2.', 699999.00, 2),
('Luxury Studio Room ', 'Hãy thư giãn trong Phòng Studio Sang trọng rộng 65m2 của chúng tôi với giường cỡ King, Smart TV, WiFi miễn phí, bếp và phòng tắm riêng cũng như minibar, tất cả đều trong không gian cách âm, không khói thuốc.', 1050000.00, 4),
('Duplex Room', 'Đắm mình trong sự sang trọng với Phòng Duplex rộng 85m2 có giường cỡ King, Smart TV, WiFi miễn phí, bếp và phòng tắm riêng, máy lạnh, hệ thống cách âm và minibar.', 1550000.00, 4);

INSERT INTO room (hotel_id, type_id, status) VALUES
(1, 1, 'available'),
(2, 2, 'available'),
(1, 3, 'available'),
(1, 4, 'available'),
(1, 5, 'available'),
(2, 6, 'available');

INSERT INTO gallery (image_url, image_format, image_type, room_type_id) VALUES
('https://storage.googleapis.com/narcissus-bucket/images/deluxe.jpg','image/jpeg', 'ACCOMMODATIONS', 1),
('https://storage.googleapis.com/narcissus-bucket/images/Superior.jpg','image/jpeg', 'ACCOMMODATIONS', 2),
('https://storage.googleapis.com/narcissus-bucket/images/Superior%20City.jpg','image/jpeg', 'ACCOMMODATIONS', 3),
('https://storage.googleapis.com/narcissus-bucket/images/Standard.jpg','image/jpeg', 'ACCOMMODATIONS', 4),
('https://storage.googleapis.com/narcissus-bucket/images/Luxury%20Studio.jpg','image/jpeg', 'ACCOMMODATIONS', 5),
('https://storage.googleapis.com/narcissus-bucket/images/duplex.jpg','image/jpeg', 'ACCOMMODATIONS', 6),
('https://storage.googleapis.com/narcissus-bucket/images/Deluxe/PSY05240.jpg','image/jpeg', 'ACCOMMODATIONS', 1),
('https://storage.googleapis.com/narcissus-bucket/images/Deluxe/PSY05252.jpg','image/jpeg', 'ACCOMMODATIONS', 1),
('https://storage.googleapis.com/narcissus-bucket/images/Deluxe/PSY05259.jpg','image/jpeg', 'ACCOMMODATIONS', 1),
('https://storage.googleapis.com/narcissus-bucket/images/Superior/A0203212.jpg','image/jpeg', 'ACCOMMODATIONS', 2),
('https://storage.googleapis.com/narcissus-bucket/images/Superior/A0203218.jpg','image/jpeg', 'ACCOMMODATIONS', 2),
('https://storage.googleapis.com/narcissus-bucket/images/Superior/A0203413.jpg','image/jpeg', 'ACCOMMODATIONS', 2),
('https://storage.googleapis.com/narcissus-bucket/images/Superior/PSY05149.jpg','image/jpeg', 'ACCOMMODATIONS', 3),
('https://storage.googleapis.com/narcissus-bucket/images/Superior/PSY05167.jpg','image/jpeg', 'ACCOMMODATIONS', 3),
('https://storage.googleapis.com/narcissus-bucket/images/Superior/PSY05188.jpg','image/jpeg', 'ACCOMMODATIONS', 3),
('https://storage.googleapis.com/narcissus-bucket/images/Standard/A0203353.jpg','image/jpeg', 'ACCOMMODATIONS', 4),
('https://storage.googleapis.com/narcissus-bucket/images/Standard/A0203359.jpg','image/jpeg', 'ACCOMMODATIONS', 4),
('https://storage.googleapis.com/narcissus-bucket/images/Standard/A0203362.jpg','image/jpeg', 'ACCOMMODATIONS', 4),
('https://storage.googleapis.com/narcissus-bucket/images/Luxury%20Studio%20Room/A0203251.jpg','image/jpeg', 'ACCOMMODATIONS', 5),
('https://storage.googleapis.com/narcissus-bucket/images/Luxury%20Studio%20Room/A0203272.jpg','image/jpeg', 'ACCOMMODATIONS', 5),
('https://storage.googleapis.com/narcissus-bucket/images/Luxury%20Studio%20Room/A0203341.jpg','image/jpeg', 'ACCOMMODATIONS', 5),
('https://storage.googleapis.com/narcissus-bucket/images/duplex/A0203266.jpg','image/jpeg', 'ACCOMMODATIONS', 6),
('https://storage.googleapis.com/narcissus-bucket/images/duplex/A0203302.jpg','image/jpeg', 'ACCOMMODATIONS', 6),
('https://storage.googleapis.com/narcissus-bucket/images/duplex/A0203329.jpg','image/jpeg', 'ACCOMMODATIONS', 6);

UPDATE room_type
SET preview_image_id =
    CASE
        WHEN type_id = 1 THEN 1
        WHEN type_id = 2 THEN 2
        WHEN type_id = 3 THEN 3
        WHEN type_id = 4 THEN 4
        WHEN type_id = 5 THEN 5
        WHEN type_id = 6 THEN 6
    END;

INSERT INTO role (name) VALUES ('STAFF'), ('MANAGER'), ('ADMIN');