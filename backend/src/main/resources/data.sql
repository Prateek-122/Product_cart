INSERT INTO roles (id, name) VALUES (1, 'ADMIN') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO roles (id, name) VALUES (2, 'CUSTOMER') ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO categories (id, parent_id, name) VALUES
    (1, NULL, 'Electronics'),
    (2, 1, 'Mobiles'),
    (3, 1, 'Laptops'),
    (4, NULL, 'Fashion'),
    (5, 4, 'Men Clothing')
ON DUPLICATE KEY UPDATE name = VALUES(name), parent_id = VALUES(parent_id);

INSERT INTO users (id, name, email, phone_hash, password_hash, created_at) VALUES
    (1, 'Admin User', 'admin@example.com', 'hashed-phone-admin', '$2a$10$3kH1pIPiV2npg8n3I1pTexbYwcmBHQYaWV7k/akdUSHhDuL2DeqVe', NOW()),
    (2, 'John Doe', 'john@example.com', 'hashed-phone-john', '$2a$10$3kH1pIPiV2npg8n3I1pTexbYwcmBHQYaWV7k/akdUSHhDuL2DeqVe', NOW()),
    (3, 'Jane Smith', 'jane@example.com', 'hashed-phone-jane', '$2a$10$3kH1pIPiV2npg8n3I1pTexbYwcmBHQYaWV7k/akdUSHhDuL2DeqVe', NOW())
ON DUPLICATE KEY UPDATE name = VALUES(name), email = VALUES(email);

INSERT INTO user_roles (user_id, role_id) VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 2)
ON DUPLICATE KEY UPDATE role_id = VALUES(role_id);

INSERT INTO products (id, sku, title, description, category_id, attributes_json, seller_id, created_at) VALUES
    (1, 'SKU-001', 'Smartphone Pro', 'Flagship smartphone with OLED display', 2, JSON_OBJECT('color', 'black', 'storage', '128GB'), 1, NOW()),
    (2, 'SKU-002', 'Ultrabook 14"', 'Lightweight laptop for professionals', 3, JSON_OBJECT('ram', '16GB', 'storage', '512GB SSD'), 1, NOW()),
    (3, 'SKU-003', 'Men Denim Jacket', 'Stylish denim jacket for men', 5, JSON_OBJECT('size', 'L', 'color', 'blue'), 1, NOW())
ON DUPLICATE KEY UPDATE title = VALUES(title), description = VALUES(description);

INSERT INTO coupons (id, code, discount_type, discount_value, user_id, product_id, valid_from, valid_to, redeemed, created_at) VALUES
    (1, 'WELCOME10', 'PERCENTAGE', 10.00, 2, 1, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), FALSE, NOW())
ON DUPLICATE KEY UPDATE code = VALUES(code), discount_type = VALUES(discount_type), discount_value = VALUES(discount_value),
    user_id = VALUES(user_id), product_id = VALUES(product_id), valid_from = VALUES(valid_from), valid_to = VALUES(valid_to),
    redeemed = VALUES(redeemed);
