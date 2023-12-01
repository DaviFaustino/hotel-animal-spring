CREATE TABLE tb_bookings (
    id_bo uuid PRIMARY KEY NOT NULL UNIQUE,
    date_check_in_bo timestamp NOT NULL,
    date_check_out_bo timestamp NOT NULL,
    cost_bo int NOT NULL,
    id_client_bo int REFERENCES tb_clients NOT NULL
);