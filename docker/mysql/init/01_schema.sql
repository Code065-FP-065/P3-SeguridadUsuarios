DROP DATABASE IF EXISTS alquiler_db;

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS alquiler_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE alquiler_db;

-- ==============================================================================
-- TABLA: cliente
-- ==============================================================================
CREATE TABLE clientes (
	id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    dni VARCHAR(20) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ==============================================================================
-- TABLA: vehiculo
-- ==============================================================================
CREATE TABLE vehiculos (
	id_vehiculo BIGINT AUTO_INCREMENT PRIMARY KEY,
    matricula VARCHAR(20) NOT NULL UNIQUE,
    marca VARCHAR(100) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    precio_dia DECIMAL(10,2) NOT NULL,
    estado ENUM ('DISPONIBLE', 'ALQUILADO', 'MANTENIMINETO') NOT NULL DEFAULT 'DISPONIBLE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT chk_vehiculo_precio_dia CHECK (precio_dia >= 0)
);

-- ==============================================================================
-- TABLA: alquiler
-- ==============================================================================
CREATE TABLE alquileres (
	id_alquiler BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_inicio DATETIME NOT NULL,
    fecha_fin DATETIME NOT NULL,
    estado ENUM ('PENDIENTE', 'EN_CURSO', 'FINALIZADO') NOT NULL DEFAULT 'PENDIENTE',
    dias INT NOT NULL,
    precio_dia_aplicado DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    id_cliente BIGINT NOT NULL,
    id_vehiculo BIGINT NOT NULL,
    
    CONSTRAINT fk_alquier_cliente FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    CONSTRAINT fk_alquiler_vehiculo FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id_vehiculo),
    
    CONSTRAINT chk_alquiler_dias CHECK (dias > 0),
    CONSTRAINT chk_alquiler_precio_dia CHECK (precio_dia_aplicado >= 0),
    CONSTRAINT chk_alquiler_total CHECK (total >= 0),
    CONSTRAINT chk_alquiler_fechas CHECK (fecha_fin IS NULL OR fecha_fin >= fecha_inicio)
);

    
    
