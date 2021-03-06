CREATE TABLE halls (id bigserial PRIMARY KEY, hallnum integer NOT NULL, rownum smallint NOT NULL,
                    colnum smallint NOT NULL, isfree boolean NOT NULL DEFAULT TRUE,
                    price real NOT NULL CHECK (price >= 0),
                    CHECK (hallnum > 0 AND rownum > 0 AND colnum > 0),
                    UNIQUE(hallnum, rownum, colnum));
INSERT INTO halls (hallnum, rownum, colnum, price) VALUES (1, 1, 1, 300), (1, 1, 2, 300), (1, 1, 3, 300),
                                                          (1, 2, 1, 400), (1, 2, 2, 400), (1, 2, 3, 400),
                                                          (1, 3, 1, 500), (1, 3, 2, 500), (1, 3, 3, 500);
CREATE TABLE accounts (id bigserial PRIMARY KEY, fullname varchar(512) NOT NULL, phone varchar(16) NOT NULL,
                       amount real NOT NULL DEFAULT 0,
                       UNIQUE(fullname, phone),
                       CONSTRAINT chk_phone CHECK (phone SIMILAR TO '_+[0-9]{11,15}' ESCAPE '_'),
                       CONSTRAINT positive_amount CHECK (amount >= 0));
INSERT INTO accounts (fullname, phone, amount) VALUES ('Иванов Иван Иванович', '+79123456789', 400),
                                                      ('Петров Пётр Петрович', '+79876543210', 1000);