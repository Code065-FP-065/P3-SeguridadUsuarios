INSERT INTO roles (nombre, descripcion) VALUES
                                            ('ADMIN', 'Administrador del sistema'),
                                            ('USER', 'Usuario cliente');

INSERT INTO usuarios (username, email, password_hash, enabled) VALUES
                                                                   ('admin', 'admin@demo.com', '$2a$10$LC1kYx7OU24mUH9UWs66HuhnvGhmabuX7VOeUMTRpkHnGY4LPvmzW', TRUE),
                                                                   ('juan',  'juan@demo.com',  '$2a$10$4enqeSfhFWo6LAuC0kvIZuwz/p8Rf0bbG8U.hIwOVdQtbG2lXDGve', TRUE),
                                                                   ('maria', 'maria@demo.com', '$2a$10$4enqeSfhFWo6LAuC0kvIZuwz/p8Rf0bbG8U.hIwOVdQtbG2lXDGve', TRUE);

INSERT INTO usuario_rol (id_usuario, id_rol) VALUES
                                                 (1, 1), -- admin -> ADMIN
                                                 (2, 2), -- juan -> USER
                                                 (3, 2); -- maria -> USER

INSERT INTO clientes (nombre, apellidos, dni, telefono, id_usuario) VALUES
                                                                        ('Juan', 'Pérez García', '12345678A', '600111111', 2),
                                                                        ('María', 'López Sánchez', '87654321B', '600222222', 3),
                                                                        ('Carlos', 'Ruiz Martín', '11223344C', '600333333', NULL);

INSERT INTO vehiculos (matricula, marca, modelo, tipo, precio_dia, estado) VALUES
                                                                               ('1234ABC', 'Toyota', 'Corolla', 'Turismo', 45.00, 'DISPONIBLE'),
                                                                               ('5678DEF', 'Seat', 'Ibiza', 'Utilitario', 38.50, 'DISPONIBLE'),
                                                                               ('9012GHI', 'Volkswagen', 'Golf', 'Turismo', 49.90, 'ALQUILADO'),
                                                                               ('3456JKL', 'Renault', 'Kangoo', 'Furgoneta', 60.00, 'MANTENIMIENTO');

INSERT INTO alquileres (
    fecha_inicio,
    fecha_fin,
    estado,
    dias,
    precio_dia_aplicado,
    total,
    id_cliente,
    id_vehiculo
) VALUES
      ('2026-04-10', '2026-04-13', 'FINALIZADO', 3, 45.00, 135.00, 1, 1),
      ('2026-04-15', '2026-04-18', 'PENDIENTE', 3, 38.50, 115.50, 2, 2),
      ('2026-04-20', '2026-04-25', 'EN_CURSO', 5, 49.90, 249.50, 1, 3);