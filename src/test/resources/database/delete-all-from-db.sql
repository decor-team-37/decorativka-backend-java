DELETE FROM product_images;

DELETE FROM products;
ALTER SEQUENCE products_id_seq RESTART WITH 1;

DELETE FROM categories;
ALTER SEQUENCE categories_id_seq RESTART WITH 1;

DELETE FROM users_roles;

DELETE FROM users;
ALTER SEQUENCE users_id_seq RESTART WITH 1;

DELETE FROM offer_images;

DELETE FROM offers;
ALTER SEQUENCE offers_id_seq RESTART WITH 1;

DELETE FROM types;
ALTER SEQUENCE types_id_seq RESTART WITH 1;
