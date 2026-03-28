# AlquilerVehiculos

Repositorio del proyecto de gestión de alquiler de vehículos desarrollado con **Java 21**, **Spring Boot**, **Maven**, **Thymeleaf**, **JPA/Hibernate** y **MySQL**.

La aplicación permite gestionar:

- clientes
- vehículos
- alquileres

El proyecto está preparado para ejecutarse:

- en entorno local
- con base de datos MySQL en Docker
- completamente con Docker

---

## Tecnologías utilizadas

- Java 21
- Spring Boot 3.5.13
- Maven
- Thymeleaf
- Spring Data JPA
- Spring Validation
- MySQL 8
- Docker
- Docker Compose

---

## Funcionalidades principales

### Clientes
- listar clientes
- crear cliente
- editar cliente
- eliminar cliente

### Vehículos
- listar vehículos
- crear vehículo
- editar vehículo
- eliminar vehículo

### Alquileres
- listar alquileres
- crear alquiler
- editar alquiler
- eliminar alquiler

### Reglas de negocio implementadas
- validación de fechas en alquileres
- cálculo automático de días de alquiler
- cálculo automático de precio diario aplicado y total
- bloqueo del borrado de clientes con alquileres asociados
- bloqueo del borrado de vehículos con alquileres asociados
- mensajes informativos de éxito y error en la interfaz

---

## Arquitectura del proyecto

El proyecto sigue una estructura por capas:

- **model**: entidades y enumerados
- **repository**: acceso a datos con Spring Data JPA
- **service**: lógica de negocio
- **controller**: controladores web MVC
- **templates**: vistas Thymeleaf
- **static**: estilos CSS y recursos estáticos

---

## Estructura de carpetas

```text
AlquilerVehiculos/
├── docker/
│   └── mysql/
│       └── init/
│           ├── 01_schema.sql
│           └── 02_data.sql
├── src/
│   └── main/
│       ├── java/
│       │   └── com/code065/alquilervehiculos/
│       │       ├── controller/
│       │       ├── model/
│       │       ├── repository/
│       │       └── service/
│       └── resources/
│           ├── static/
│           ├── templates/
│           ├── application.properties
│           ├── application-local.properties
│           ├── application-docker.properties
│           └── application-container.properties
├── compose.yaml
├── Dockerfile
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

## Base de datos

La base de datos utilizada es **MySQL**.

Los scripts SQL del proyecto se encuentran en:

```text
docker/mysql/init
```

### Archivos SQL
- `01_schema.sql`: creación de base de datos, tablas y claves foráneas
- `02_data.sql`: inserción de datos iniciales de prueba

La base de datos se ha configurado para trabajar con codificación **UTF-8 / utf8mb4**.

---

## Requisitos previos

Para ejecutar el proyecto en local:

- Java 21
- IntelliJ IDEA o terminal
- MySQL local o Docker
- Maven Wrapper incluido en el proyecto

Para ejecutar el proyecto con Docker:

- Docker
- Docker Compose

---

## Perfiles de configuración

El proyecto utiliza perfiles de Spring Boot para facilitar el cambio entre entornos.

### Archivos de configuración
- `application.properties` → configuración común
- `application-local.properties` → MySQL local
- `application-docker.properties` → MySQL en Docker y aplicación en local
- `application-container.properties` → aplicación y base de datos en Docker

---

## Ejecución en local con MySQL local

### 1. Preparar MySQL local
Debes disponer de un servidor MySQL local y de una base de datos llamada:

```text
alquiler_vehiculos
```

### 2. Revisar el perfil local
En el archivo `application-local.properties` configura tus credenciales locales.

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/alquiler_vehiculos?useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8mb4_unicode_ci
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_LOCAL
```

### 3. Ejecutar desde IntelliJ
Ejecuta la clase principal del proyecto con el siguiente perfil activo:

```text
--spring.profiles.active=local
```

### 4. Ejecutar desde terminal
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

---

## Ejecución con MySQL en Docker y aplicación en local

Este modo permite ejecutar la base de datos en Docker mientras la aplicación Spring Boot se ejecuta desde IntelliJ o terminal.

### 1. Levantar MySQL en Docker
```bash
docker compose up -d mysql
```

### 2. Verificar perfil docker
El archivo `application-docker.properties` debe apuntar al puerto publicado del contenedor MySQL.

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/alquiler_vehiculos?useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8mb4_unicode_ci
spring.datasource.username=appuser
spring.datasource.password=apppass
```

### 3. Ejecutar desde IntelliJ
Usa el perfil:

```text
--spring.profiles.active=docker
```

### 4. Ejecutar desde terminal
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=docker
```

---

## Ejecución completa con Docker

En este modo se ejecutan tanto la base de datos MySQL como la aplicación Spring Boot dentro de contenedores.

### 1. Levantar todo el entorno
```bash
docker compose down -v
docker compose up --build
```

### 2. Acceder a la aplicación
Una vez iniciado el entorno completo:

```text
http://localhost:8080
```

---

## Reinicialización de la base de datos en Docker

Los scripts ubicados en `docker/mysql/init` se ejecutan **solo la primera vez** que MySQL inicializa el volumen de datos.

Si se desea volver a ejecutar los scripts desde cero, es necesario eliminar los volúmenes y reconstruir el entorno:

```bash
docker compose down -v
docker compose up --build
```

---

## Rutas principales de la aplicación

- Dashboard / Inicio  
  `http://localhost:8080/`

- Clientes  
  `http://localhost:8080/clientes`

- Vehículos  
  `http://localhost:8080/vehiculos`

- Alquileres  
  `http://localhost:8080/alquileres`

---

## Notas de implementación

- La propiedad `spring.jpa.hibernate.ddl-auto=none` se utiliza para que Hibernate no cree ni modifique la estructura de la base de datos automáticamente.
- La creación de la base de datos, tablas, relaciones y carga inicial de datos se controla mediante scripts SQL propios del proyecto.
- El menú de navegación resalta dinámicamente la sección activa.
- La interfaz reutiliza fragments de Thymeleaf para:
    - cabecera
    - navegación
    - pie
    - mensajes de éxito y error

---

## Flujo recomendado para el tutor

### Opción 1: ejecución completa con Docker
```bash
docker compose down -v
docker compose up --build
```

Después abrir:

```text
http://localhost:8080
```

### Opción 2: MySQL en Docker y aplicación en IntelliJ
1. Ejecutar:
```bash
docker compose up -d mysql
```

2. Ejecutar la aplicación con el perfil:

```text
--spring.profiles.active=docker
```

---

## Grupo

Proyecto realizado por el grupo **Code065**.

---
