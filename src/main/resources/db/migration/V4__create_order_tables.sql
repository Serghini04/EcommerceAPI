CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    constraint orders_users_id_fk
        FOREIGN KEY (customer_id) REFERENCES user (id)
);

CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    quantity BIGINT NOT NULL,
    total_price BIGINT NOT NULL,
    CONSTRAINT order_items_orders_fk
        FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT order_item_products_id_fk
        FOREIGN KEY (product_id) REFERENCES products (id)
);