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
(2, 2, 'available'),
(1, 3, 'available'),
(1, 4, 'available'),
(1, 5, 'available'),
(2, 6, 'available');

INSERT INTO gallery (image_url, image_format, image_type, room_type_id) VALUES
('https://storage.googleapis.com/narcissus-bucket/images/deluxe.jpg','image/jpeg', 'ACCOMMODATIONS', 1),
('https://storage.googleapis.com/narcissus-bucket/images/Superior.jpg','image/jpeg', 'ACCOMMODATIONS', 2),
('https://storage.googleapis.com/narcissus-bucket/images/Superior%20City.png','image/png', 'ACCOMMODATIONS', 3),
('https://storage.googleapis.com/narcissus-bucket/images/Standard.png','image/png', 'ACCOMMODATIONS', 4),
('https://storage.googleapis.com/narcissus-bucket/images/Luxury%20Studio.png','image/png', 'ACCOMMODATIONS', 5),
('https://storage.googleapis.com/narcissus-bucket/images/duplex.png','image/png', 'ACCOMMODATIONS', 6),
('https://storage.googleapis.com/narcissus-bucket/images/Deluxe/PSY05240.jpg','image/jpeg', 'ACCOMMODATIONS', 1),
('https://storage.googleapis.com/narcissus-bucket/images/Deluxe/PSY05252.jpg','image/jpeg', 'ACCOMMODATIONS', 1),
('https://storage.googleapis.com/narcissus-bucket/images/Deluxe/PSY05259.jpg','image/jpeg', 'ACCOMMODATIONS', 1),
('https://storage.googleapis.com/narcissus-bucket/images/Superior/A0203212.png','image/png', 'ACCOMMODATIONS', 2),
('https://storage.googleapis.com/narcissus-bucket/images/Superior/A0203218.png','image/png', 'ACCOMMODATIONS', 2),
('https://storage.googleapis.com/narcissus-bucket/images/Superior/A0203413.png','image/png', 'ACCOMMODATIONS', 2),
('https://storage.googleapis.com/narcissus-bucket/images/Superior/PSY05149.jpg','image/jpeg', 'ACCOMMODATIONS', 3),
('https://storage.googleapis.com/narcissus-bucket/images/Superior/PSY05167.jpg','image/jpeg', 'ACCOMMODATIONS', 3),
('https://storage.googleapis.com/narcissus-bucket/images/Superior/PSY05188.jpg','image/jpeg', 'ACCOMMODATIONS', 3),
('https://storage.googleapis.com/narcissus-bucket/images/Standard/A0203353.png','image/png', 'ACCOMMODATIONS', 4),
('https://storage.googleapis.com/narcissus-bucket/images/Standard/A0203359.png','image/png', 'ACCOMMODATIONS', 4),
('https://storage.googleapis.com/narcissus-bucket/images/Standard/A0203362.png','image/png', 'ACCOMMODATIONS', 4),
('https://storage.googleapis.com/narcissus-bucket/images/Luxury%20Studio%20Room/A0203251.png','image/png', 'ACCOMMODATIONS', 5),
('https://storage.googleapis.com/narcissus-bucket/images/Luxury%20Studio%20Room/A0203272.png','image/png', 'ACCOMMODATIONS', 5),
('https://storage.googleapis.com/narcissus-bucket/images/Luxury%20Studio%20Room/A0203341.png','image/png', 'ACCOMMODATIONS', 5),
('https://storage.googleapis.com/narcissus-bucket/images/duplex/A0203266.png','image/png', 'ACCOMMODATIONS', 6),
('https://storage.googleapis.com/narcissus-bucket/images/duplex/A0203302.png','image/png', 'ACCOMMODATIONS', 6),
('https://storage.googleapis.com/narcissus-bucket/images/duplex/A0203329.png','image/png', 'ACCOMMODATIONS', 6);

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