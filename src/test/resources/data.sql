-- Clear DB and reset ID
DELETE FROM hotels
ALTER TABLE hotels ALTER COLUMN id RESTART WITH 1

-- Insert new rows
INSERT INTO hotels (city, room, price) VALUES ('Bangkok','Superior', 1400)
INSERT INTO hotels (city, room, price) VALUES ('Amsterdam','Deluxe', 2300)
INSERT INTO hotels (city, room, price) VALUES ('Bangkok','Deluxe', 1900)