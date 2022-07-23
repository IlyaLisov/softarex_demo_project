create table answers (dtype varchar(31) not null,
                      id uuid not null,
                      created timestamp,
                      status varchar(255),
                      updated timestamp,
                      answer_type varchar(255),
                      date_answer timestamp,
                      multiline_answer varchar(255),
                      radiobox_answer varchar(255),
                      single_line_answer varchar(255),
                      primary key (id));

create table checkbox_answer_entity_answer (checkbox_answer_entity_id uuid not null,
                                            checkbox_answer varchar(255));

create table checkbox_answer_entity_options (checkbox_answer_entity_id uuid not null,
                                             checkbox_options varchar(255));

create table questions (id uuid not null,
                        created timestamp,
                        status varchar(255),
                        updated timestamp,
                        question varchar(255),
                        answer_entity_id uuid,
                        author_id uuid,
                        recipient_id uuid,
                        primary key (id));

create table radio_button_answer_entity_options (radio_button_answer_entity_id uuid not null,
                                                 radiobox_options varchar(255));

create table users (id uuid not null,
                    created timestamp,
                    status varchar(255),
                    updated timestamp,
                    first_name varchar(255),
                    last_name varchar(255),
                    password varchar(255),
                    phone_number varchar(255),
                    username varchar(255),
                    primary key (id));

create table user_roles (user_id uuid not null,
                         roles varchar(255) not null);

alter table if exists users add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);

alter table if exists checkbox_answer_entity_answer add constraint FKd6v7cx6wij1jvnp4it610uu6d foreign key (checkbox_answer_entity_id) references answers;

alter table if exists checkbox_answer_entity_options add constraint FK5se1d3717oom056i59xmg1x7d foreign key (checkbox_answer_entity_id) references answers;

alter table if exists questions add constraint FKohc2e05pam1x8k85bwno0o1iq foreign key (answer_entity_id) references answers on delete cascade;

alter table if exists questions add constraint FKii8pqtr2qjv47ht06bg8vtl9n foreign key (author_id) references users on delete cascade;

alter table if exists questions add constraint FKgmvwst3cp1nn7eyqb18ewp8sy foreign key (recipient_id) references users on delete cascade;

alter table if exists radio_button_answer_entity_options add constraint FKb5rc3nkwx4fy1qfn2k1p18eer foreign key (radio_button_answer_entity_id) references answers;

alter table if exists user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users;