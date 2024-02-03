CREATE SEQUENCE IF NOT EXISTS country_id_seq START WITH 1 INCREMENT BY 1;

INSERT INTO country (id, name) VALUES (nextval('country_id_seq'), 'Tree Kingdom'), (nextval('country_id_seq'),'Red Empire'), (nextval('country_id_seq'),'Green Panda');

INSERT INTO language (name) VALUES ('English'), ('Spanish'), ('French'), ('German');

INSERT INTO city (name, country_id) VALUES ('Dark city', 1), ('Green city', 2);

INSERT INTO film (title, description, length, release_year, language_id, rental_duration, rental_rate, replacement_cost, special_features, last_update) VALUES ('Eternal Sunshine of the Spotless Mind', 'A couple undergoes a procedure to erase each other from their memories.', 108, 2004, 1, 3, 4.99, 19.99, 'Commentaries,Deleted Scenes', CURRENT_TIMESTAMP), ('Inception', 'A thief who steals corporate secrets through dream-sharing technology.', 148, 2010, 1, 3, 4.99, 19.99, 'Behind the Scenes', CURRENT_TIMESTAMP), ('The Matrix', 'A computer hacker learns about the true nature of his reality.', 136, 1999, 1, 3, 4.99, 19.99, 'Trailers,Commentaries', CURRENT_TIMESTAMP), ('Pulp Fiction', 'The lives of two mob hitmen, a boxer, and a pair of diner bandits intertwine.', 154, 1994, 1, 3, 4.99, 19.99, 'Deleted Scenes', CURRENT_TIMESTAMP), ('Fight Club', 'An insomniac office worker and a devil-may-care soapmaker form an underground fight club.', 139, 1999, 1, 3, 4.99, 19.99, 'Commentaries', CURRENT_TIMESTAMP), ('Forrest Gump', 'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man.', 142, 1994, 1, 3, 4.99, 19.99, 'Behind the Scenes', CURRENT_TIMESTAMP), ('The Godfather', 'The aging patriarch of an organized crime dynasty transfers control to his reluctant son.', 175, 1972, 1, 3, 4.99, 19.99, 'Trailers,Deleted Scenes', CURRENT_TIMESTAMP), ('The Dark Knight', 'When the menace known as the Joker emerges, he wreaks havoc and chaos.', 152, 2008, 1, 3, 4.99, 19.99, 'Commentaries,Behind the Scenes', CURRENT_TIMESTAMP);

INSERT INTO address (address, district, city_id, phone) VALUES ('23 Workhaven Lane', 'Alberta', 1, '14033335568'), ('1411 Lillydale Drive', 'QLD', 2, '14033335568'), ('100 Main Street', 'Central', 1, '1234567890'), ('101 First Avenue', 'North', 1, '1234567891'), ('102 Second Street', 'East', 2, '1234567892'), ('103 Third Avenue', 'West', 2, '1234567893'), ('104 Fourth Street', 'South', 1, '1234567894'), ('105 Fifth Avenue', 'Northeast', 2, '1234567895'), ('106 Sixth Street', 'Northwest', 1, '1234567896'), ('107 Seventh Avenue', 'Southeast', 2, '1234567897');

INSERT INTO store (name, address_id, last_update) VALUES ('Galaxy Far Far Away', 3, CURRENT_TIMESTAMP), ('Middle Earth', 4, CURRENT_TIMESTAMP);

INSERT INTO staff (address_id, store_id, first_name, last_name, email, username, password, active, last_update) VALUES (5, 1, 'John', 'Doe', 'john.doe@store.com', 'johndoe', 'password123', TRUE, CURRENT_TIMESTAMP), (6, 2, 'Jane', 'Doe', 'jane.doe@store.com', 'janedoe', 'password456', TRUE, CURRENT_TIMESTAMP);

INSERT INTO category (name) VALUES ('Action'), ('Animation'), ('Children'), ('Classics'), ('Comedy'), ('Documentary'), ('Drama'), ('Family'), ('Foreign'), ('Games'), ('Horror'), ('Music'), ('New'), ('Sci-Fi'), ('Sports'), ('Travel');

INSERT INTO actor (first_name, last_name) VALUES ('PENELOPE', 'GUINESS'), ('NICK', 'WAHLBERG'), ('ED', 'CHASE'), ('JENNIFER', 'DAVIS'), ('JOHNNY', 'LOLLOBRIGIDA'), ('BETTE', 'NICHOLSON'), ('GRACE', 'MOSTEL'), ('MATTHEW', 'JOHANSSON'), ('JOE', 'SWANK'), ('CHRISTIAN', 'GABLE');

INSERT INTO customer (store_id, first_name, last_name, address_id, email, active, create_date, last_update) VALUES (1, 'John', 'Doe', 1, 'john.doe@example.com', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), (2, 'Jane', 'Doe', 2, 'jane.doe@example.com', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), (1, 'Jim', 'Beam', 3, 'jim.beam@example.com', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO inventory (film_id, store_id, last_update) VALUES (1, 1, CURRENT_TIMESTAMP), (5, 2, CURRENT_TIMESTAMP), (2, 1, CURRENT_TIMESTAMP), (6, 2, CURRENT_TIMESTAMP), (3, 1, CURRENT_TIMESTAMP), (7, 2, CURRENT_TIMESTAMP), (4, 1, CURRENT_TIMESTAMP), (8, 2, CURRENT_TIMESTAMP), (1, 1, CURRENT_TIMESTAMP), (5, 2, CURRENT_TIMESTAMP), (2, 1, CURRENT_TIMESTAMP), (6, 2, CURRENT_TIMESTAMP), (3, 1, CURRENT_TIMESTAMP), (7, 2, CURRENT_TIMESTAMP), (4, 1, CURRENT_TIMESTAMP), (8, 2, CURRENT_TIMESTAMP);

-- Associating films with 'Action'
INSERT INTO film_category (category_id, film_id) VALUES (1, 3), /*The Matrix as Action*/ (1, 7), /*The Godfather as Action*/ (1, 8); -- The Dark Knight as Action
-- Associating films with 'Comedy'
INSERT INTO film_category (category_id, film_id) VALUES (2, 4), /*Pulp Fiction as Comedy*/ (2, 5), /*Fight Club as Comedy*/ (2, 6); -- Forrest Gump as Comedy
-- Associating films with 'Drama'
INSERT INTO film_category (category_id, film_id) VALUES (3, 1), /*Eternal Sunshine of the Spotless Mind as Drama*/ (3, 2), /*Inception as Drama*/ (3, 6); -- Forrest Gump as Drama
-- Associating films with 'Sci-Fi'
INSERT INTO film_category (category_id, film_id) VALUES (4, 1), /*Eternal Sunshine of the Spotless Mind as Sci-Fi*/ (4, 2), /*Inception as Sci-Fi*/ (4, 3); -- The Matrix as Sci-Fi

-- Associating actors with 'Eternal Sunshine of the Spotless Mind' (Film ID 1)
INSERT INTO actor_film (actor_id, film_id) VALUES (1, 1), (2, 1), (3, 1);
-- Associating actors with 'Inception' (Film ID 2)
INSERT INTO actor_film (actor_id, film_id) VALUES (4, 2), (5, 2), (6, 2);
-- Associating actors with 'The Matrix' (Film ID 3)
INSERT INTO actor_film (actor_id, film_id) VALUES (7, 3), (8, 3), (9, 3);
-- Associating actors with 'Pulp Fiction' (Film ID 4)
INSERT INTO actor_film (actor_id, film_id) VALUES (1, 4), (10, 4), (3, 4);
-- Repeating the pattern for the remaining films
INSERT INTO actor_film (actor_id, film_id) VALUES (2, 5), (4, 5), (6, 5), (5, 6), (7, 6), (8, 6), (9, 7), (10, 7), (1, 7), (2, 8), (3, 8), (4, 8);

INSERT INTO rental (inventory_id, customer_id, staff_id, store_id, rental_date, return_date, last_update) VALUES (1, 1, 1, 1, '2023-01-01 00:00:00', '2023-01-05 00:00:00', '2023-01-05 00:00:00'), (1, 2, 2, 2, '2023-02-01 00:00:00', '2023-02-05 00:00:00', '2023-02-05 00:00:00'), (1, 3, 1, 1, '2023-03-01 00:00:00', NULL, CURRENT_TIMESTAMP);

INSERT INTO payment (customer_id, staff_id, rental_id, amount, payment_date, last_Update) VALUES (1, 1, 1, 15.00, '2023-01-01 00:00:00', '2023-01-05 00:00:00'), (2, 2, 2, 15.00, '2023-02-01 00:00:00', '2023-02-05 00:00:00'), (3, 1, 3, 20.00, '2023-03-01 00:00:00', CURRENT_TIMESTAMP);