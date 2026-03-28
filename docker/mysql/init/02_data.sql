USE alquiler_vehiculos;
SET NAMES utf8mb4;

INSERT INTO clientes (nombre, apellidos, dni, telefono)
VALUES 
('Xavi', 'Miró Carrera', '12345678A', '600111222'),
('Ana', 'López García', '87654321B', '600333444');

INSERT INTO vehiculos (matricula, marca, modelo, tipo, precio_dia, estado)
VALUES
('1234ABC', 'Toyota', 'Corolla', 'Turismo', 45.00, 'DISPONIBLE'),
('5678DEF', 'Ford', 'Transit', 'Furgoneta', 80.00, 'DISPONIBLE');

INSERT INTO alquileres (
    fecha_inicio, fecha_fin, estado, dias, precio_dia_aplicado, total, id_cliente, id_vehiculo
)
VALUES
('2026-04-10', '2026-04-12', 'FINALIZADO', 3, 45.00, 135.00, 1, 1),
('2026-04-15', '2026-04-20', 'EN_CURSO', 2, 80.00, 160.00, 2, 2);