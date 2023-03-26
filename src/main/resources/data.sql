INSERT INTO elevator (id, hotel_id, name, is_restricted, current_floor) VALUES
(1, 1, 'Elevator A', FALSE, 0),
(2, 1, 'Elevator B', FALSE, 0),
(3, 2, 'Elevator A', TRUE, 0),
(4, 2, 'Elevator B', TRUE, 0),
(5, 3, 'Elevator A', FALSE, 0);

INSERT INTO hotel (id, name, num_floors) VALUES
(1, 'Grand Hotel', 10),
(2, 'Luxury Resort', 20),
(3, 'Boutique Hotel', 30);