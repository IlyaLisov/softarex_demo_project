insert into users (id, created, status, updated, first_name, last_name, password, username, phone_number)
values ('1835c810-040f-11ed-b939-0242ac120002', '2022-07-15 10:26:18.000000', 'ACTIVE', '2022-07-15 10:26:18.000000',
        'Ilya', 'Lisov', '$2a$12$v0h9ik7npOfBw1g6u1Yuq.YFI0AHxhpxRpOc59fdtYHW42CY4rxDu',
        'ilya.lisov.yt@gmail.com', '+375298012370'),
       ('c72f54d0-040f-11ed-b939-0242ac120002', '2022-07-15 10:26:18.000000', 'ACTIVE', '2022-07-15 10:26:18.000000',
        'Ivan', '', '$2a$12$v0h9ik7npOfBw1g6u1Yuq.YFI0AHxhpxRpOc59fdtYHW42CY4rxDu',
        'ivan@gmail.com', '+375291234567'),
       ('d1c05d22-040f-11ed-b939-0242ac120002', '2022-07-15 10:26:18.000000', 'ACTIVE', '2022-07-15 10:26:18.000000',
        'Test', 'User', '$2a$12$v0h9ik7npOfBw1g6u1Yuq.YFI0AHxhpxRpOc59fdtYHW42CY4rxDu',
        'test@test.com', '');

insert into user_roles (user_id, roles)
values ('1835c810-040f-11ed-b939-0242ac120002', 'ROLE_USER'),
       ('c72f54d0-040f-11ed-b939-0242ac120002', 'ROLE_USER'),
       ('d1c05d22-040f-11ed-b939-0242ac120002', 'ROLE_USER');

insert into answers (dtype, id, created, status, updated, answer_type)
values ('SingleLineAnswerEntity', 'db838f5e-0410-11ed-b939-0242ac120002', '2022-07-15 10:26:18.000000', 'ACTIVE', '2022-07-15 10:26:18.000000',
        'SINGLE_LINE_TEXT');

insert into questions (id, created, status, updated, question, answer_entity_id, author_id, recipient_id)
values ('69717400-0413-11ed-b939-0242ac120002', '2022-07-15 10:26:18.000000', 'ACTIVE', '2022-07-15 10:26:18.000000', 'How are you?',
        'db838f5e-0410-11ed-b939-0242ac120002', '1835c810-040f-11ed-b939-0242ac120002',
        'c72f54d0-040f-11ed-b939-0242ac120002');