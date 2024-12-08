-- Insert Sample Users into Users Table
INSERT INTO app_users (username, password, role, unlocked) VALUES
                                                           ('admin', '$2y$10$sPrbT7GKWFKWBC.1fLbA7eT1RQP09A/hGnVb9N5vy3EyYgGnR5OMa', 'ROLE_ADMIN', TRUE),
                                                           ('user1', '$2y$10$sPrbT7GKWFKWBC.1fLbA7eT1RQP09A/hGnVb9N5vy3EyYgGnR5OMa', 'ROLE_USER', TRUE),
                                                           ('user2', '$2y$10$sPrbT7GKWFKWBC.1fLbA7eT1RQP09A/hGnVb9N5vy3EyYgGnR5OMa', 'ROLE_USER', TRUE),
                                                           ('locked_user', '$2y$10$sPrbT7GKWFKWBC.1fLbA7eT1RQP09A/hGnVb9N5vy3EyYgGnR5OMa', 'ROLE_USER', FALSE);


-- Insert Sample Data into Households
INSERT INTO households (eircode, number_of_occupants, max_number_of_occupants, owner_occupied) VALUES
                                                                                                   ('EIR123', 3, 5, TRUE),
                                                                                                   ('EIR456', 2, 4, FALSE),
                                                                                                   ('EIR789', 0, 2, TRUE),
                                                                                                   ('EIR101', 5, 5, TRUE),
                                                                                                   ('EIR202', 1, 3, FALSE);

-- Insert Sample Data into Pets
INSERT INTO pets (name, animal_type, breed, age, eircode) VALUES
                                                              ('Buddy', 'Dog', 'Labrador', 3, 'EIR123'),
                                                              ('Mittens', 'Cat', 'Persian', 2, 'EIR123'),
                                                              ('Charlie', 'Dog', 'Beagle', 5, 'EIR456'),
                                                              ('Goldie', 'Fish', 'Goldfish', 1, 'EIR456'),
                                                              ('Max', 'Dog', 'German Shepherd', 6, 'EIR101');
