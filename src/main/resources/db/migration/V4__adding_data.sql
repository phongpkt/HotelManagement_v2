INSERT INTO hotel (name, address, phone_number) VALUES
('Narcissus Hotel & Apartment', ' No. 33 Tuc Mac lane, Cua Nam ward, Hoan Kiem district, Ha Noi, Viet Nam', '0337248276'),
('Narcissus Hotel & Apartment', 'No. 84 Le Duan, Van Mieu ward, Hoan Kiem district, Ha Noi, Viet Nam', '0337248276');

INSERT INTO room_type (name, description, price_per_night, capacity) VALUES
('Deluxe Room', 'Experience luxury in our 40m² Deluxe Room, with a private kitchen, Smart TV, soundproofing, minibar, and free WiFi. Your ultimate retreat awaits!', 1350000.00, 2),
('Superior Room', 'Experience luxury in our 38m² Superior Room, with a private kitchen, Smart TV, soundproofing, minibar, and free WiFi. Your ultimate retreat awaits!', 1050000.00, 2),
('Superior City Room', 'Discover comfort in our Superior City Room featuring a king-size bed, Smart TV, free WiFi, and captivating city views.', 832000.00, 2),
('Standard Room', 'Indulge in comfort with our Standard Room, offering a king-size bed, Smart TV, free WiFi, and a smoke-free environment across 25m² of space.', 699999.00, 2),
('Luxury Studio Room ', 'Luxuriate in our 65m² Luxury Studio Room with a king-size bed, Smart TV, free WiFi, private kitchen and bathroom, and minibar, all in a smoke-free, soundproofed space.', 1050000.00, 4),
('Duplex Room', 'Immerse yourself in luxury with our 85m² Duplex Room featuring a king-size bed, Smart TV, free WiFi, private kitchen and bathroom, air conditioning, soundproofing, and a minibar', 1550000.00, 4);

INSERT INTO room (hotel_id, type_id, status) VALUES
(1, 1, 'available'),
(1, 2, 'available'),
(1, 3, 'available'),
(2, 4, 'available'),
(2, 5, 'available'),
(2, 6, 'available');

INSERT INTO role (name) VALUES ('STAFF'), ('MANAGER'), ('ADMIN');