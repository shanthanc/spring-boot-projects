create table if not exists ibo(
    ibo_number bigint primary key not null,
    first_name varchar(20) not null,
    last_name varchar(20) not null,
    address varchar(1000) not null,
    date_of_birth date not null 
);