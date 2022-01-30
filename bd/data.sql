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