INSERT INTO clientes (nombre_completo, email) VALUES ('Gabriela Camacho', 'gabriela@example.com');
INSERT INTO clientes (nombre_completo, email) VALUES ('Ismael López', 'ismael.lopez@example.com');
INSERT INTO clientes (nombre_completo, email) VALUES ('Milagros Pucci', 'milagros.pucci@example.com');

INSERT INTO puestosjuego (nombre, tipo) VALUES ('Puesto 1', 'Consola');
INSERT INTO puestosjuego (nombre, tipo) VALUES ('Puesto 2', 'PC Gamer');
INSERT INTO puestosjuego (nombre, tipo) VALUES ('Puesto 3', 'Realidad Virtual');

INSERT INTO videojuegos (titulo, genero) VALUES ('FIFA 24', 'Deportes');
INSERT INTO videojuegos (titulo, genero) VALUES ('Call of Duty', 'Shooter');
INSERT INTO videojuegos (titulo, genero) VALUES ('Minecraft', 'Aventura');

INSERT INTO reservas (cliente_id, videojuego_id, puestojuego_id, fecha_hora, duracion_minutos, observaciones)
VALUES (1, 2, 1, '2025-08-01T14:00:00', 60, 'Llega 10 minutos antes');

INSERT INTO reservas (cliente_id, videojuego_id, puestojuego_id, fecha_hora, duracion_minutos, observaciones)
VALUES (2, 1, 2, '2025-08-01T16:00:00', 90, 'Trae control propio');

INSERT INTO reservas (cliente_id, videojuego_id, puestojuego_id, fecha_hora, duracion_minutos, observaciones)
VALUES (3, 3, 3, '2025-08-02T11:00:00', 45, 'Primera vez usando VR');
