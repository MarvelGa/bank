INSERT INTO users (first_name, last_name, email, passwords, `role`, birth_day)
VALUES
( 'Ivan', 'Ivanov','ivan@gmail.com',  '111', 'manager', '2010-01-13' ),
( 'Petro', 'Petrov','petro@gmail.com',  '222', 'manager', '2005-01-13' ),
( 'Lana', 'Ray','lana@gmail.com',  '333', 'client', '2001-01-13' ),
( 'Vasilisa', 'Trubenko','trubenko@gmail.com',  '444', 'client', '2000-01-13' );

INSERT INTO managers (spacialization, user_id)
VALUES
( 'credits', 1),
( 'ipoteka', 2);

INSERT INTO clients (user_id)
VALUES
( 3),
( 4);

INSERT INTO accounts (manager_id, client_id)
VALUES
( 1, 1),
( 1, 2);

INSERT INTO cards (`number`, amount, currency, account_id, expiration_date) VALUES
('525654555', 2000000, 'USD', 2, '2026-02-16'),
('553535353', 5000000, 'UAH', 2, '2030-01-15'),
('533858545', 7000000, 'EUR', 2, '2025-05-11');