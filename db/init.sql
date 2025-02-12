INSERT INTO customers (first_name, last_name, username, password)
VALUES 
    ('John', 'Doe', 'johndoe', 'password123'),
    ('Jane', 'Smith', 'janesmith', 'password456');

INSERT INTO metering_points (customer_id, address)
VALUES 
    ((SELECT id FROM customers WHERE username = 'johndoe'), '123 Main Street'),
    ((SELECT id FROM customers WHERE username = 'johndoe'), '456 Elm Street'),
    ((SELECT id FROM customers WHERE username = 'johndoe'), '789 Oak Street');

INSERT INTO consumption (metering_point_id, amount, amount_unit, consumption_time)
VALUES 
    -- January
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.5, 'MW', '2024-01-10 14:00:00'),
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.2, 'MW', '2024-01-25 16:00:00'),
    
    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.7, 'MW', '2024-01-12 09:30:00'),
    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.8, 'MW', '2024-01-27 18:45:00'),

    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.3, 'MW', '2024-01-15 11:15:00'),
    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.4, 'MW', '2024-01-28 22:10:00'),

    -- February
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.5, 'MW', '2024-02-05 13:10:00'),
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.2, 'MW', '2024-02-20 15:50:00'),
    
    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.2, 'MW', '2024-02-08 10:00:00'),
    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.9, 'MW', '2024-02-22 17:20:00'),

    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.6, 'MW', '2024-02-14 08:40:00'),
    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.1, 'MW', '2024-02-28 21:00:00'),

    -- March
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.4, 'MW', '2024-03-03 12:25:00'),
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.3, 'MW', '2024-03-18 16:40:00'),

    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.9, 'MW', '2024-03-07 09:10:00'),
    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.7, 'MW', '2024-03-23 18:30:00'),

    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.5, 'MW', '2024-03-11 10:55:00'),
    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.6, 'MW', '2024-03-29 23:00:00'),

    -- April
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.4, 'MW', '2024-04-03 12:25:00'),
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.3, 'MW', '2024-04-18 16:40:00'),

    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.9, 'MW', '2024-04-07 09:10:00'),
    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.7, 'MW', '2024-04-23 18:30:00'),

    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.5, 'MW', '2024-04-11 10:55:00'),
    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.6, 'MW', '2024-04-29 23:00:00'),

    -- May
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.4, 'MW', '2024-05-03 12:25:00'),
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.3, 'MW', '2024-05-18 16:40:00'),

    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.9, 'MW', '2024-05-07 09:10:00'),
    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.7, 'MW', '2024-05-23 18:30:00'),

    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.5, 'MW', '2024-05-11 10:55:00'),
    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.6, 'MW', '2024-05-29 23:00:00'),

    -- June
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.4, 'MW', '2024-06-03 12:25:00'),
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.3, 'MW', '2024-06-18 16:40:00'),

    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.9, 'MW', '2024-06-07 09:10:00'),
    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.7, 'MW', '2024-06-23 18:30:00'),

    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.5, 'MW', '2024-06-11 10:55:00'),
    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.6, 'MW', '2024-06-29 23:00:00'),

    -- July
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.4, 'MW', '2024-07-03 12:25:00'),
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.3, 'MW', '2024-07-18 16:40:00'),

    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.9, 'MW', '2024-07-07 09:10:00'),
    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.7, 'MW', '2024-07-23 18:30:00'),

    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.5, 'MW', '2024-07-11 10:55:00'),
    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.6, 'MW', '2024-07-29 23:00:00'),

    -- August
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.4, 'MW', '2024-08-03 12:25:00'),
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.3, 'MW', '2024-08-18 16:40:00'),

    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.9, 'MW', '2024-08-07 09:10:00'),
    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.7, 'MW', '2024-08-23 18:30:00'),

    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.5, 'MW', '2024-08-11 10:55:00'),
    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.6, 'MW', '2024-08-29 23:00:00'),

    -- September
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.4, 'MW', '2024-09-03 12:25:00'),
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.3, 'MW', '2024-09-18 16:40:00'),

    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.9, 'MW', '2024-09-07 09:10:00'),
    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.7, 'MW', '2024-09-23 18:30:00'),

    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.5, 'MW', '2024-09-11 10:55:00'),
    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.6, 'MW', '2024-09-29 23:00:00'),

    -- October
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.4, 'MW', '2024-10-03 12:25:00'),
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.3, 'MW', '2024-10-18 16:40:00'),

    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.9, 'MW', '2024-10-07 09:10:00'),
    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.7, 'MW', '2024-10-23 18:30:00'),

    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.5, 'MW', '2024-10-11 10:55:00'),
    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.6, 'MW', '2024-10-29 23:00:00'),

    -- November
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.4, 'MW', '2024-11-03 12:25:00'),
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.2, 'MW', '2024-11-18 16:40:00'),

    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.2, 'MW', '2024-11-07 09:10:00'),
    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.2, 'MW', '2024-11-23 18:30:00'),

    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.3, 'MW', '2024-11-11 10:55:00'),
    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.1, 'MW', '2024-11-29 23:00:00'),

    -- December
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.4, 'MW', '2024-12-03 12:25:00'),
    ((SELECT id FROM metering_points WHERE address = '123 Main Street' LIMIT 1), 1.3, 'MW', '2024-12-18 16:40:00'),

    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.3, 'MW', '2024-12-07 09:10:00'),
    ((SELECT id FROM metering_points WHERE address = '456 Elm Street' LIMIT 1), 1.3, 'MW', '2024-12-23 18:30:00'),

    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.2, 'MW', '2024-12-11 10:55:00'),
    ((SELECT id FROM metering_points WHERE address = '789 Oak Street' LIMIT 1), 1.4, 'MW', '2024-12-29 23:00:00');
