INSERT INTO hotel (name, address, phone_number) VALUES
('Narcissus Hotel & Apartment', '33 ngõ Tục Mặc, phường Cửa Nam, quận Hoàn Kiếm, Hà Nội, Việt Nam', '(+84.24)7303 2662'),
('Narcissus Hotel & Apartment', '84 Lê Duẩn, phường Văn Miếu, quận Hoàn Kiếm, Hà Nội, Việt Nam', '(+84.24)7303 2662');

INSERT INTO room_type (name, description, price_per_night, capacity) VALUES
('Superior city room', 'Smart TV, 1 King bed, 35m², Non-smoking, Free Wifi, City view', 832000.00, 2),
('Standard room', 'Smart TV, 1 King bed, 25m², Non-smoking, Free Wifi', 699000.00, 2),
('Luxury studio room', 'Smart TV, 1 King bed, 65m², Non-smoking, Free Wifi, studio nguyên căn 65 m², Bếp riêng, Phòng tắm riêng, Điều hòa không khí, Hệ thống cách âm, Minibar', 1050000.00, 2),
('Duplex room', 'Smart TV, 1 King bed, 85m², Non-smoking, Free Wifi, studio nguyên căn 85 m², Bếp riêng, Phòng tắm riêng, Điều hòa không khí, Hệ thống cách âm, Minibar', 1550000.00, 2),
('Deluxe', 'Studio nguyên căn, 40m², Bếp riêng, Phòng tắm riêng trong phòng, Điều hòa không khí, Smart TV, Hệ thống cách âm, Minibar, WiFi miễn phí - Ăn sáng phục vụ tại phòng - Cho thuê xe máy', 1350000.00, 4),
('Superior', 'Studio nguyên căn, 38 m², Bếp riêng, Phòng tắm riêng trong phòng, Điều hòa không khí, Smart TV, Hệ thống cách âm, Minibar, WiFi miễn phí - Ăn sáng phục vụ tại phòng - Cho thuê xe máy', 1050000.00, 4);

INSERT INTO room (hotel_id, type_id, status) VALUES
(1, 1, 'available'),
(1, 2, 'available'),
(1, 3, 'available'),
(2, 4, 'available'),
(2, 5, 'available'),
(2, 6, 'available');

INSERT INTO role (name) VALUES ('STAFF'), ('MANAGER'), ('ADMIN');
