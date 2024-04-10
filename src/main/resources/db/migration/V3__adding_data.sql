INSERT INTO hotel (name, address, phone_number) VALUES
('Narcissus Hotel & Apartment', '33 ngõ Tục Mặc, phường Cửa Nam, quận Hoàn Kiếm, Hà Nội, Việt Nam', '(+84.24)7303 2662'),
('Narcissus Hotel & Apartment', '84 Lê Duẩn, phường Văn Miếu, quận Hoàn Kiếm, Hà Nội, Việt Nam', '(+84.24)7303 2662');

INSERT INTO staff (hotel_id, first_name, last_name, phone, email, password, role) VALUES
(1, 'Admin', '1', '1234567890', 'vinhnguyen.admin1@gmail.com', 'Vinhdeptrai', 'admin'),
(2, 'Admin', '2', '1234567890', 'vinhnguyen.admin2@gmail.com', 'Vinhdeptrai', 'admin');

INSERT INTO room_type (name, description, price_per_night, capacity) VALUES
('Superior city room', 'Smart TV, 1 King bed, 35m², Non-smoking, Free Wifi, City view', 832000.00, 2),
('Standard room', 'Smart TV, 1 King bed, 25m², Non-smoking, Free Wifi', 699000.00, 2),
('Luxury studio room', 'Smart TV, 1 King bed, 65m², Non-smoking, Free Wifi, studio nguyên căn 65 m², Bếp riêng, Phòng tắm riêng, Điều hòa không khí, Hệ thống cách âm, Minibar', 1050000.00, 2),
('Duplex room', 'Smart TV, 1 King bed, 85m², Non-smoking, Free Wifi, studio nguyên căn 85 m², Bếp riêng, Phòng tắm riêng, Điều hòa không khí, Hệ thống cách âm, Minibar', 1550000.00, 2),
('Deluxe', 'Studio nguyên căn, 40m², Bếp riêng, Phòng tắm riêng trong phòng, Điều hòa không khí, Smart TV, Hệ thống cách âm, Minibar, WiFi miễn phí - Ăn sáng phục vụ tại phòng - Cho thuê xe máy', 1350000.00, 4),
('Superior', 'Studio nguyên căn, 38 m², Bếp riêng, Phòng tắm riêng trong phòng, Điều hòa không khí, Smart TV, Hệ thống cách âm, Minibar, WiFi miễn phí - Ăn sáng phục vụ tại phòng - Cho thuê xe máy', 1050000.00, 4);

INSERT INTO room (hotel_id, type_id, status) VALUES
(1, 1, 'Available'),
(1, 2, 'Available'),
(1, 3, 'Available'),
(2, 4, 'Available'),
(2, 5, 'Available'),
(2, 6, 'Available');
