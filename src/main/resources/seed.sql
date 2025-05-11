INSERT INTO CLIENTE_ENTITY (
    id, nombre_Usuario, correo_Usuario, contrasena_Usuario, tiene_Subscripcion
) VALUES
(1, 'Felipe Tontin', 'pipin@gmail.com', 'pipin123', TRUE);

INSERT INTO TEATRO_ENTITY (
    id, nombre_Teatro, direccion_Teatro, capacidad, capacidad_Teatro
) VALUES
(1, 'Teatro Colón', 'Calle 10 # 5-32, Bogotá', 784, 784),
(2, 'Teatro Metropolitano José Gutiérrez Gómez', 'Calle 41 # 57-30, Medellín', 1634, 1634),
(3, 'Teatro Jorge Eliécer Gaitán', 'Cra. 7 # 22-47, Bogotá', 1896, 1896),
(4, 'Teatro Pablo Tobón Uribe', 'Calle 51 # 40-13, Medellín', 900, 900);

INSERT INTO OBRA_DE_TEATRO_ENTITY (
    id, titulo, genero, duracion, sinopsis, elenco, imagen
) VALUES
(1, 'La casa de Bernarda Alba', 'Drama', 120, 'Una obra escrita por Federico García Lorca sobre una madre autoritaria y sus hijas.', 'Reparto 1', 'https://ammonralibreria.com/wp-content/uploads/2024/07/9788426352644.jpg'),
(2, 'Hamlet', 'Tragedia', 180, 'Tragedia de William Shakespeare sobre el príncipe Hamlet y su venganza.', 'Reparto 2', 'https://www.getabstract.com/summary-img/31682-JZXYF2Y8.jpg?h=M'),
(3, 'La fiesta del chivo', 'Drama', 150, 'Basada en la novela de Vargas Llosa sobre la dictadura en República Dominicana.', 'Reparto 3', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSyv7YKF9N3pT0rUNYkbr7YU6vrdzExqtIkTQ&s'),
(4, 'El rey león', 'Musical', 150, 'Musical de Disney que narra la historia de Simba.', 'Reparto 4', 'https://lumiere-a.akamaihd.net/v1/images/image_956cb480.jpeg');

INSERT INTO RESENA_ENTITY (
    id, comentario, calificacion, cliente_id, obra_De_Teatro_id, teatro_id
) VALUES
(1, 'Una obra maestra que explora la opresión y la libertad.', 5, 1, 1, 1),
(2, 'Hamlet es profunda y sorprendente.', 4, 1, 2, 2),
(3, 'Impactante visión de la dictadura.', 5, 1, 3, 3),
(4, 'Visualmente espectacular.', 5, 1, 4, 4);

INSERT INTO EVENTO_ESPECIAL_ENTITY (
    id, nombre_Evento, descripcion_Evento, fecha, teatro_id
) VALUES
(1, 'Estreno de La casa de Bernarda Alba', 'Estreno en el Teatro Colón.', '2023-10-01', 1),
(2, 'Funciones de Hamlet', 'Hamlet en el Teatro Metropolitano.', '2023-10-05', 2),
(3, 'La fiesta del chivo en vivo', 'Presentación en el Jorge Eliécer Gaitán.', '2023-10-10', 3),
(4, 'El rey león en Bogotá', 'Especial en Bogotá.', '2023-10-15', 4);

INSERT INTO FUNCION_ENTITY (
    id, fecha, hora, obra_De_Teatro_id, teatro_id
) VALUES
(1, '2023-10-01', '19:00', 1, 1),
(2, '2023-10-05', '20:00', 2, 2),
(3, '2023-10-10', '18:30', 3, 3),
(4, '2023-10-15', '17:00', 4, 4);

INSERT INTO PAGO_ENTITY (
    id, metodo_pago, monto
) VALUES
(1, 'Tarjeta de Crédito', 25000.00),
(2, 'Efectivo', 30000.00),
(3, 'Transferencia', 28000.00),
(4, 'Tarjeta de Crédito', 32000.00);

INSERT INTO ASIENTO_ENTITY (
    id, numero, teatro_id, disponible
) VALUES
(1, 1, 1, TRUE),
(2, 2, 1, TRUE),
(3, 3, 2, TRUE),
(4, 4, 2, TRUE),
(5, 5, 3, TRUE),
(6, 6, 3, TRUE),
(7, 7, 4, TRUE),
(8, 8, 4, TRUE);

INSERT INTO BOLETO_ENTITY (
    id, precio, funcion_id, cliente_id, silla_id, pago_id, evento_especial_id
) VALUES
(1, 25000.00, 1, 1, 1, 1, 1),
(2, 30000.00, 2, 1, 2, 2, 2),
(3, 28000.00, 3, 1, 3, 3, 3),
(4, 32000.00, 4, 1, 4, 4, 4);
