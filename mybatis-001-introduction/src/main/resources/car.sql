-- practice on the 動力節點
DROP TABLE IF EXISTS car;
CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    car_num VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    guide_price DECIMAL(10, 2) NOT NULL,
    produce_time CHAR(10) NOT NULL,
    car_type VARCHAR(255) NOT NULL
);
