

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(28),
    stock INT,
    company INT NOT NULL, 

    CONSTRAINT fk_company
        FOREIGN KEY (company)
        REFERENCES company (id)
        ON DELETE CASCADE 
);

drop table product;

create table company(
id SERIAL primary key,
CIF char(9) not null unique,
name varchar(28) not null,
telephone char(9),
email varchar(50),
direction varchar(100),
login varchar(20) not null unique,
password varchar(255) not null
);


drop table company;