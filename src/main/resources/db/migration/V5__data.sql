# Insert initial data
# Categories :
INSERT INTO categories (id, name) VALUES (1, 'Electronics'), (2, 'Books'), (3, 'Clothing');

# Products :
INSERT INTO products (id, name, description, price, category_id) VALUES
(1, 'Smartphone', 'Latest model smartphone', 699.99, 1),
(2, 'Laptop', 'Powerful gaming laptop', 1299.99, 1),
(3, 'Novel', 'Bestselling fiction novel', 19.99, 2),
(4, 'T-Shirt', 'Cotton t-shirt with logo', 24.99, 3);

# Users:
INSERT INTO users (id, name, email, password, role) VALUES
(1, 'King', 'mehdi@example.com', '$2a$10$Rpj8ITF9DAXobx3vKlYY8ueiVt26hxpErdi7OJlL0RBAEDbz8h3Au', 'USER'),
(2, 'Mehdi', 'm.serghini@edu.com', '$2a$10$lKGBH1.AqHjH.NEg9OY5N.cjkRyF9S9IaDKeuRbguUXk6McROF.Em', 'USER');

# Carts:
INSERT INTO carts (id, date_created) VALUES 
('�O?H˳���;g', '2025-09-02');
