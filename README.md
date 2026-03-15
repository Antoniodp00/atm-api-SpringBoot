# API REST - Sistema de Gestión de Cajeros Automáticos (ATM)

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

API RESTful profesional desarrollada con **Spring Boot** para la gestión y monitorización de una red de Cajeros Automáticos. El sistema permite controlar el estado del hardware, administrar personal técnico, auditar transacciones financieras y gestionar el stock de repuestos durante las reparaciones.

> Proyecto desarrollado para la asignatura de **Acceso a Datos (2º DAM)**.

---

## Tabla de Contenidos

- [Características Principales](#-características-principales)
- [Tecnologías y Arquitectura](#-tecnologías-y-arquitectura)
- [Instalación y Configuración](#-instalación-y-configuración)
- [Documentación de la API](#-documentación-de-la-api-swagger)
- [Testing](#-testing)
- [Autor](#-autor)

---

## Características Principales

### Gestión de Hardware y Personal
Operaciones CRUD completas para Cajeros, Técnicos y Repuestos, implementando *Soft Delete* (borrado lógico).

### Lógica de Negocio Transaccional
Registro de consumo de piezas asociadas a averías con validación de stock en tiempo real mediante métodos `@Transactional`.

### Consultas Complejas (JOINs)
- Identificación de cajeros en **estado crítico** (incidencias de prioridad alta no resueltas).
- Detección de **técnicos sobrecargados** mediante agrupaciones (`GROUP BY` y `HAVING`).
- **Filtrado cruzado** (Cuádruple JOIN) para saber qué repuestos se consumen más en una ciudad específica.

### Auditoría Financiera
Cálculo dinámico de estadísticas de operaciones (ingresos, retiros) aprovechando funciones de agregación de la base de datos.

### Manejo de Excepciones
Interceptor global (`@RestControllerAdvice`) para devolver respuestas JSON estructuradas y limpias (errores 400 y 404).

---

## Tecnologías y Arquitectura

El proyecto sigue una estricta **Arquitectura en Capas**: Controlador → Servicio → Repositorio → Modelo → DTOs.

| Categoría | Tecnología |
|---|---|
| Lenguaje | Java (JDK 17+) |
| Framework | Spring Boot (Web, Data JPA, Validation) |
| Base de Datos | MySQL |
| ORM | Hibernate |
| Documentación | Springdoc OpenAPI (Swagger UI) |
| Testing | JUnit 5 + Mockito |

---

## Instalación y Configuración

### 1. Prerrequisitos

- Java **JDK 17** o superior instalado.
- Servidor **MySQL** corriendo (p. ej. XAMPP, MySQL Workbench).

### 2. Base de datos

Crea la base de datos en tu servidor MySQL antes de arrancar la aplicación:

```sql
CREATE DATABASE sistema_cajeros;
```

### 3. Configuración de credenciales

Edita `src/main/resources/application.properties` y ajusta las credenciales según tu entorno local:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_cajeros
spring.datasource.username=root
spring.datasource.password=tu_contraseña
```

### 4. Ejecución

Arranca la clase `AtmApiApplication.java` desde tu IDE, o usa Maven desde la terminal:

```bash
./mvnw spring-boot:run
```

> **Nota:** Hibernate creará automáticamente las tablas y relaciones al arrancar la aplicación.

---

## 📖 Documentación de la API (Swagger)

Con la aplicación corriendo, accede a la interfaz interactiva de Swagger:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Testing

Batería de pruebas unitarias sobre la **capa de Servicios** con **Mockito**, sin necesidad de levantar el contexto de base de datos:

```bash
./mvnw test
```

---

## Autor

**Antonio Delgado Portero** — Desarrollo completo
