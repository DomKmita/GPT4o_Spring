-- Households Table
CREATE TABLE households (
                            eircode VARCHAR(20) PRIMARY KEY,
                            number_of_occupants INT NOT NULL,
                            max_number_of_occupants INT NOT NULL,
                            owner_occupied BOOLEAN NOT NULL
);

-- Pets Table
CREATE TABLE pets (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(50) NOT NULL,
                      animal_type VARCHAR(50) NOT NULL,
                      breed VARCHAR(50),
                      age INT NOT NULL,
                      eircode VARCHAR(20) NOT NULL,
                      FOREIGN KEY (eircode) REFERENCES households(eircode) ON DELETE CASCADE
);
