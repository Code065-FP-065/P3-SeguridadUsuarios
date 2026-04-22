DROP TABLE IF EXISTS usuario_rol;
DROP TABLE IF EXISTS alquileres;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS vehiculos;

CREATE TABLE usuarios (
                          id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
                          username VARCHAR(50) NOT NULL UNIQUE,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          password_hash VARCHAR(255) NOT NULL,
                          enabled BOOLEAN NOT NULL DEFAULT TRUE,
                          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles (
                       id_rol BIGINT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(30) NOT NULL UNIQUE,
                       descripcion VARCHAR(255)
);

CREATE TABLE usuario_rol (
                             id_usuario BIGINT NOT NULL,
                             id_rol BIGINT NOT NULL,
                             assigned_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             PRIMARY KEY (id_usuario, id_rol),
                             CONSTRAINT fk_usuario_rol_usuario
                                 FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
                                     ON DELETE CASCADE,
                             CONSTRAINT fk_usuario_rol_rol
                                 FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
                                     ON DELETE CASCADE
);

CREATE TABLE clientes (
                          id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          apellidos VARCHAR(150) NOT NULL,
                          dni VARCHAR(20) NOT NULL UNIQUE,
                          telefono VARCHAR(30) NOT NULL,
                          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          id_usuario BIGINT UNIQUE,
                          CONSTRAINT fk_cliente_usuario
                              FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
                                  ON DELETE SET NULL
);

CREATE TABLE vehiculos (
                           id_vehiculo BIGINT AUTO_INCREMENT PRIMARY KEY,
                           matricula VARCHAR(20) NOT NULL UNIQUE,
                           marca VARCHAR(100) NOT NULL,
                           modelo VARCHAR(100) NOT NULL,
                           tipo VARCHAR(50) NOT NULL,
                           precio_dia DECIMAL(10,2) NOT NULL,
                           estado VARCHAR(30) NOT NULL,
                           created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT chk_vehiculo_estado
                               CHECK (estado IN ('DISPONIBLE', 'ALQUILADO', 'MANTENIMIENTO'))
);

CREATE TABLE alquileres (
                            id_alquiler BIGINT AUTO_INCREMENT PRIMARY KEY,
                            fecha_inicio DATE NOT NULL,
                            fecha_fin DATE NOT NULL,
                            estado VARCHAR(30) NOT NULL,
                            dias INT NOT NULL,
                            precio_dia_aplicado DECIMAL(10,2) NOT NULL,
                            total DECIMAL(10,2) NOT NULL,
                            created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            id_cliente BIGINT NOT NULL,
                            id_vehiculo BIGINT NOT NULL,
                            CONSTRAINT fk_alquiler_cliente
                                FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
                                    ON DELETE RESTRICT,
                            CONSTRAINT fk_alquiler_vehiculo
                                FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id_vehiculo)
                                    ON DELETE RESTRICT,
                            CONSTRAINT chk_alquiler_estado
                                CHECK (estado IN ('PENDIENTE', 'EN_CURSO', 'FINALIZADO')),
                            CONSTRAINT chk_alquiler_dias
                                CHECK (dias > 0),
                            CONSTRAINT chk_alquiler_total
                                CHECK (total >= 0),
                            CONSTRAINT chk_alquiler_fechas
                                CHECK (fecha_fin > fecha_inicio)
);
    
    
