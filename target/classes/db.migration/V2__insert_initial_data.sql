-- Dodawanie podstawowych ról
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

-- Wstawienie przykładowych użytkowników
INSERT INTO users (username, password, email, first_name, last_name, phone_number, address)
VALUES
('john_doe', 'password123', 'john.doe@example.com', 'John', 'Doe', '1234567890', 'ul. Piękna 12, Warszawa'),
('jane_smith', 'password321', 'jane.smith@example.com', 'Jane', 'Smith', '1287654321', 'ul. Lechitów 45, Kraków');

-- Wstawienie właścicieli lokali gastronomicznych
INSERT INTO restaurant_owners (user_id)
VALUES
((SELECT id FROM users WHERE username = 'john_doe'));

-- Wstawienie restauracji (właściciel wskazany przez owner_id)
INSERT INTO restaurants (owner_id, name, address, phone_number, email, description)
VALUES
((SELECT id FROM restaurant_owners WHERE user_id = (SELECT id FROM users WHERE username = 'john_doe')), 'Pizzeria Bella', 'ul. Kwiatowa 10, Warszawa', '22-123-456', 'kontakt@pizzeriabella.pl', 'Pyszna pizza na cienkim cieście.'),
((SELECT id FROM restaurant_owners WHERE user_id = (SELECT id FROM users WHERE username = 'john_doe')), 'Sushi Mistrz', 'ul. Leśna 5, Kraków', '12-345-678', 'info@sushimistrz.pl', 'Autentyczne sushi przygotowywane przez mistrza kuchni.');

-- Wstawienie pozycji menu
INSERT INTO menu_items (restaurant_id, name, description, price, image_url)
VALUES
((SELECT id FROM restaurants WHERE name = 'Pizzeria Bella'), 'Pizza Margherita', 'Klasyczna pizza z sosem pomidorowym i bazylią.', 29.99, 'http://example.com/images/pizza_margherita.jpg'),
((SELECT id FROM restaurants WHERE name = 'Pizzeria Bella'), 'Pizza Pepperoni', 'Ostra pizza z pepperoni i serem mozzarella.', 34.99, 'http://example.com/images/pizza_pepperoni.jpg'),
((SELECT id FROM restaurants WHERE name = 'Sushi Mistrz'), 'Sushi California', 'Świeży krab i awokado zawinięte w wodorosty.', 24.99, 'http://example.com/images/sushi_california.jpg'),
((SELECT id FROM restaurants WHERE name = 'Sushi Mistrz'), 'Sushi Spicy Tuna', 'Ostry tuńczyk z ogórkiem i awokado.', 29.99, 'http://example.com/images/sushi_spicy_tuna.jpg');

-- Wstawienie kategorii menu
INSERT INTO categories (name)
VALUES
('Przystawki'),
('Dania główne'),
('Desery'),
('Napoje');

-- Powiązanie pozycji menu z kategoriami
INSERT INTO menu_item_categories (menu_item_id, category_id)
VALUES
((SELECT id FROM menu_items WHERE name = 'Pizza Margherita'), (SELECT id FROM categories WHERE name = 'Dania główne')),
((SELECT id FROM menu_items WHERE name = 'Pizza Pepperoni'), (SELECT id FROM categories WHERE name = 'Dania główne')),
((SELECT id FROM menu_items WHERE name = 'Sushi California'), (SELECT id FROM categories WHERE name = 'Dania główne')),
((SELECT id FROM menu_items WHERE name = 'Sushi Spicy Tuna'), (SELECT id FROM categories WHERE name = 'Dania główne'));

-- Wstawienie obszarów dostawy
INSERT INTO delivery_areas (restaurant_id, street_name)
VALUES
((SELECT id FROM restaurants WHERE name = 'Pizzeria Bella'), 'ul. Ogrodowa 10, Warszawa'),
((SELECT id FROM restaurants WHERE name = 'Pizzeria Bella'), 'ul. Leśna 5, Warszawa'),
((SELECT id FROM restaurants WHERE name = 'Sushi Mistrz'), 'ul. Żółwia 3, Kraków'),
((SELECT id FROM restaurants WHERE name = 'Sushi Mistrz'), 'ul. Polan 7, Kraków');

-- Przypisanie ról do użytkowników
INSERT INTO user_roles (user_id, role_id)
VALUES
((SELECT id FROM users WHERE username = 'john_doe'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')),
((SELECT id FROM users WHERE username = 'jane_smith'), (SELECT id FROM roles WHERE name = 'ROLE_USER'));
