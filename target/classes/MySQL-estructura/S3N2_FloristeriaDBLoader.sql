DROP DATABASE IF EXISTS t3n2Floristeria;
CREATE DATABASE t3n2Floristeria CHARACTER SET utf8mb4;
USE t3n2Floristeria;

CREATE TABLE datos_floristeria (
	id BIGINT UNSIGNED NOT NULL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL
);
    
CREATE TABLE producto (
	id BIGINT UNSIGNED NOT NULL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    precio FLOAT,
    tipo VARCHAR(30),
    cantidad INT NOT NULL
);

CREATE TABLE arbol (
	id BIGINT UNSIGNED NOT NULL,
    altura FLOAT,
    FOREIGN KEY (id) REFERENCES producto(id)
);

CREATE TABLE flor (
	id BIGINT UNSIGNED NOT NULL,
    color VARCHAR(30),
    FOREIGN KEY (id) REFERENCES producto(id)
);

CREATE TABLE decoracion (
	id BIGINT UNSIGNED NOT NULL,
    material ENUM ('MADERA', 'PLASTICO'),
    FOREIGN KEY (id) REFERENCES producto(id)
);

CREATE TABLE ticket (
	id BIGINT UNSIGNED NOT NULL PRIMARY KEY,
    fecha DATETIME
);

CREATE TABLE producto_ticket (
	ticketId BIGINT UNSIGNED NOT NULL,
    productoId BIGINT UNSIGNED NOT NULL,
    cantidad INT NOT NULL,
    FOREIGN KEY (ticketId) REFERENCES ticket(id),
    FOREIGN KEY (productoId) REFERENCES producto(id)
);

INSERT INTO producto VALUES (1, "A1", 5.5, "arbol", 100);
INSERT INTO producto VALUES (2, "A2", 5.5, "arbol", 100);
INSERT INTO producto VALUES (3, "F1", 5.5, "flor", 100);
INSERT INTO producto VALUES (4, "F2", 5.5, "flor", 100);
INSERT INTO producto VALUES (5, "D1", 5.5, "decoracion", 100);
INSERT INTO producto VALUES (6, "D2", 5.5, "decoracion", 100);

INSERT INTO arbol VALUES (1, 3.5);
INSERT INTO arbol VALUES (2, 3.5);

INSERT INTO flor VALUES (3, "Rojo");
INSERT INTO flor VALUES (4, "Violeta");

INSERT INTO decoracion VALUES (5, 'MADERA');
INSERT INTO decoracion VALUES (6, 'PLASTICO');

INSERT INTO ticket VALUES (1, '2020-01-01 00:01:00');
INSERT INTO ticket VALUES (2, '2020-01-01 00:01:00');

INSERT INTO producto_ticket VALUES (1, 1, 5);
UPDATE producto SET cantidad = 95 WHERE producto.id = 1;
INSERT INTO producto_ticket VALUES (1, 3, 10);
UPDATE producto SET cantidad = 90 WHERE producto.id = 3;

INSERT INTO producto_ticket VALUES (2, 2, 10);
UPDATE producto SET cantidad = 90 WHERE producto.id = 2;