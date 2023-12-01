CREATE TABLE tb_clients (
    id_cl SERIAL PRIMARY KEY NOT NULL UNIQUE,
    name_cl varchar(50) NOT NULL,
    phone_cl char(15) NOT NULL
);