CREATE TABLE weather (
    id SERIAL PRIMARY KEY,
    city VARCHAR(255) NOT NULL,
    temperature DOUBLE PRECISION
);