-- Create table for Users
CREATE TABLE IF NOT EXISTS users
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name    VARCHAR(255)        NOT NULL,
    last_name     VARCHAR(255)        NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    created_date  TIMESTAMP,
    modified_date TIMESTAMP
);

-- Create table for Products
CREATE TABLE IF NOT EXISTS products
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    code          VARCHAR(15) UNIQUE NOT NULL,
    name          VARCHAR(255)       NOT NULL,
    price_eur     DECIMAL(10, 2),
    price_usd     DECIMAL(10, 2),
    description   TEXT,
    created_date  TIMESTAMP,
    modified_date TIMESTAMP
);

-- Create table for Reviews
CREATE TABLE IF NOT EXISTS reviews
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id    BIGINT    NOT NULL,
    reviewer_id   BIGINT    NOT NULL,
    text          TEXT,
    rating        INT CHECK (rating >= 1 AND rating <= 5),
    created_date  TIMESTAMP,
    modified_date TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (reviewer_id) REFERENCES users (id)
);