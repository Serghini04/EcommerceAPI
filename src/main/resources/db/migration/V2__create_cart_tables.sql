CREATE TABLE carts (
    id BINARY(16) DEFAULT (uuid_to_bin(uuid())) NOT NULL,
    date_created date DEFAULT (curdate()) NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT UNSIGNED DEFAULT 1 NULL,
    cart_id BINARY(16) NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT cart_items_cart_product_unique UNIQUE (cart_id, product_id),
    CONSTRAINT cart_items_carts_id_fk
        FOREIGN KEY (cart_id) REFERENCES carts (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);