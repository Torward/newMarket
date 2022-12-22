create table products
(
    id         bigserial primary key,
    title      varchar(255),
    price      int,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into products (title, price)
values ('Milk', 100),
       ('Bread', 80),
       ('Bread2', 80),
       ('Bread3', 80),
       ('Bread4', 80),
       ('Bread5', 80),
       ('Bread6', 80),
       ('Bread7', 80),
       ('Bread8', 80),
       ('Bread9', 80),
       ('Bread10', 80),
       ('Bread11', 80),
       ('Bread12', 80),
       ('Bread13', 80),
       ('Bread14', 80),
       ('Bread15', 80),
       ('Bread16', 80),
       ('Bread17', 80),
       ('Bread18', 80),
       ('Bread19', 80),
       ('Bread20', 80),
       ('Bread21', 80),
       ('Bread22', 80),
       ('Bread23', 80),
       ('Bread24', 80),
       ('Bread25', 80),
       ('Cheese', 90);


create table orders
(
    id          bigserial primary key,
    username    varchar(255) not null,
    total_price int    not null,
    address     varchar(255),
    phone       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table order_items
(
    id                bigserial primary key,
    product_id        bigint not null references products (id),
    order_id          bigint not null references orders (id),
    quantity          int    not null,
    price_per_product int    not null,
    price             int    not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);

insert into orders (username, total_price, address, phone)
VALUES ('bob', 200, 'address', '12345');

insert into order_items (product_id, order_id, quantity, price_per_product, price)
VALUES (1,1,2,100, 200);
