INSERT INTO users (name, email)
VALUES ('Peter', 'peter@gmail.com'),
       ('Sergey', 'sergey@yandex.ru'),
       ('Ivan', 'ivan@mail.ru');

INSERT INTO requests (description, create_date, author_id)
VALUES ('request1', '2024-10-31 10:00', 1),
       ('request2', '2024-10-31 11:00', 2);

INSERT INTO items (name, description, available, owner_id, request_id)
VALUES ('Item 1', 'Text', true, 1, null),
       ('Item 2', 'Text', false, 2, 2);

INSERT INTO bookings (start_date, end_date, item_id, booker_id, status)
VALUES ('2024-10-31 10:10', '2025-11-11 12:00', 1, 2, 'WAITING'),
       ('2024-10-31 10:10', '2024-10-31 12:00', 2, 3, 'APPROVED');