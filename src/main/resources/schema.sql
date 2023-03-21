CREATE TABLE IF NOT EXISTS elevator (
    id BIGINT NOT NULL AUTO_INCREMENT,
    current_floor INT NOT NULL,
    hotel_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    is_restricted BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);