# Biblioteca API

## Idea del Proyecto

El proyecto consiste en desarrollar una API para gestionar una biblioteca. Este sistema permitirá manejar recursos clave como libros, autores, categorías, usuarios, préstamos, reservas y comentarios, facilitando el control y la interacción con una base de datos que almacena toda esta información.

## Justificación del Proyecto

La gestión eficiente de una biblioteca es esencial para proporcionar un buen servicio a los usuarios. Este proyecto busca automatizar y optimizar procesos como:
- Registro y búsqueda de libros.
- Gestión de autores y categorías.
- Control de préstamos y reservas.
- Seguimiento de usuarios y sus actividades.
- Interacción con comentarios sobre los libros.

Con este sistema, se reduce el tiempo dedicado a tareas administrativas y se mejora la experiencia del usuario.

## Tablas Involucradas

### 1. **Autores (Authors)**

| Atributo         | Tipo          | Descripción                              |
|------------------|---------------|------------------------------------------|
| **ID**           | Long          | Clave primaria, autoincremental.         |
| **Nombre**       | VARCHAR(255)  | Nombre del autor (único y no nulo).      |
| **Nacionalidad** | VARCHAR(255)  | País de origen del autor.                |

### 2. **Libros (Books)**

| Atributo         | Tipo          | Descripción                                               |
|------------------|---------------|-----------------------------------------------------------|
| **ID**           | Long          | Clave primaria, autoincremental.                          |
| **Título**       | VARCHAR(255)  | Título del libro (no nulo).                               |
| **ISBN**         | VARCHAR(13)   | Código único del libro, de 13 caracteres.                 |
| **AutorID**      | Long          | Clave foránea que referencia a la tabla **Authors**.      |
| **CategoríaID**  | Long          | Clave foránea que referencia a la tabla **Categories**.   |

### 3. **Categorías (Categories)**

| Atributo        | Tipo          | Descripción                              |
|-----------------|---------------|------------------------------------------|
| **ID**          | Long          | Clave primaria, autoincremental.         |
| **Nombre**      | VARCHAR(255)  | Nombre de la categoría (único y no nulo).|

### 4. **Usuarios (Users)**

| Atributo       | Tipo                  | Descripción                                               |
|----------------|-----------------------|-----------------------------------------------------------|
| **ID**         | Long                  | Clave primaria, autoincremental.                          |
| **Nombre**     | VARCHAR(255)          | Primer nombre del usuario (no nulo).                      |
| **Apellido**   | VARCHAR(255)          | Apellido del usuario (no nulo).                           |
| **Email**      | VARCHAR(255)          | Dirección de correo única (no nula).                      |
| **Password**   | VARCHAR(255)          | Contraseña en formato seguro.                             |
| **Rol**        | ENUM(`ADMIN`, `USER`) | Rol del usuario en el sistema.                  |

### 5. **Préstamos (Loans)**

| Atributo           | Tipo          | Descripción                                               |
|--------------------|---------------|-----------------------------------------------------------|
| **ID**            | Long          | Clave primaria, autoincremental.                          |
| **Fecha Préstamo** | DATE          | Fecha en que el préstamo fue realizado (no nula).         |
| **Fecha Devolución** | DATE        | Fecha en que se espera la devolución (no nula).           |
| **UsuarioID**     | Long          | Clave foránea que referencia a la tabla **Users**.        |
| **LibroID**       | Long          | Clave foránea que referencia a la tabla **Books**.        |

### 6. **Reservas (Reservations)**

| Atributo          | Tipo          | Descripción                                               |
|-------------------|---------------|-----------------------------------------------------------|
| **ID**            | Long          | Clave primaria, autoincremental.                          |
| **Fecha Reserva** | DATE          | Fecha en que se realizó la reserva (no nula).             |
| **Estado**        | ENUM(`PENDING`, `COMPLETED`, `CANCELLED`) | Estado actual de la reserva.|
| **UsuarioID**     | Long          | Clave foránea que referencia a la tabla **Users**.        |
| **LibroID**       | Long          | Clave foránea que referencia a la tabla **Books**.        |

### 7. **Comentarios (Comments)**

| Atributo        | Tipo          | Descripción                                               |
|-----------------|---------------|-----------------------------------------------------------|
| **ID**         | Long          | Clave primaria, autoincremental.                          |
| **Contenido**  | VARCHAR(500)  | Texto del comentario (máximo 500 caracteres, no nulo).    |
| **UsuarioID**  | Long          | Clave foránea que referencia a la tabla **Users**.        |
| **LibroID**    | Long          | Clave foránea que referencia a la tabla **Books**.        |

## Relaciones entre Tablas

- **Autores a Libros**: Un autor puede escribir muchos libros (**1:N**). La tabla **Books** contiene la clave foránea `AutorID` que referencia a **Authors**.
- **Categorías a Libros**: Una categoría puede contener muchos libros (**1:N**). La tabla **Books** contiene la clave foránea `CategoríaID` que referencia a **Categories**.
- **Usuarios a Préstamos**: Un usuario puede tener varios préstamos (**1:N**). La tabla **Loans** contiene la clave foránea `UsuarioID` que referencia a **Users**.
- **Usuarios a Reservas**: Un usuario puede realizar varias reservas (**1:N**). La tabla **Reservations** contiene la clave foránea `UsuarioID` que referencia a **Users**.
- **Usuarios a Comentarios**: Un usuario puede realizar varios comentarios (**1:N**). La tabla **Comments** contiene la clave foránea `UsuarioID` que referencia a **Users**.
- **Libros a Comentarios**: Un libro puede tener varios comentarios (**1:N**). La tabla **Comments** contiene la clave foránea `LibroID` que referencia a **Books**.
- **Libros a Préstamos**: Un libro puede tener varios préstamos (**1:N**). La tabla **Loans** contiene la clave foránea `LibroID` que referencia a **Books**.
- **Libros a Reservas**: Un libro puede estar reservado múltiples veces (**1:N**). La tabla **Reservations** contiene la clave foránea `LibroID` que referencia a **Books**.

## Endpoints de la API

A continuación, se detallan los endpoints para cada tabla de la base de datos:

### Endpoints para **Autores (Authors)**

| **Método** | **Endpoint**       | **Descripción**                                      | **Acceso**       |
|------------|--------------------|------------------------------------------------------|------------------|
| **GET**    | `/authors`         | Obtiene la lista de todos los autores registrados.   | Todos            |
| **GET**    | `/authors/{id}`    | Obtiene los detalles de un autor específico por ID.  | Todos            |
| **POST**   | `/authors`         | Crea un nuevo autor.                                 | Solo Admin       |
| **PUT**    | `/authors/{id}`    | Actualiza los datos de un autor por ID.              | Solo Admin       |
| **DELETE** | `/authors/{id}`    | Elimina un autor por ID.                             | Solo Admin       |

---

### Endpoints para **Libros (Books)**

| **Método** | **Endpoint**       | **Descripción**                                      | **Acceso**       |
|------------|--------------------|------------------------------------------------------|------------------|
| **GET**    | `/books`           | Obtiene la lista de todos los libros registrados.    | Todos            |
| **GET**    | `/books/{id}`      | Obtiene los detalles de un libro específico por ID.  | Todos            |
| **POST**   | `/books`           | Crea un nuevo libro.                                 | Solo Admin       |
| **PUT**    | `/books/{id}`      | Actualiza los datos de un libro por ID.              | Solo Admin       |
| **DELETE** | `/books/{id}`      | Elimina un libro por ID.                             | Solo Admin       |

---

### Endpoints para **Categorías (Categories)**

| **Método** | **Endpoint**       | **Descripción**                                      | **Acceso**       |
|------------|--------------------|------------------------------------------------------|------------------|
| **GET**    | `/categories`      | Obtiene la lista de todas las categorías.            | Todos            |
| **GET**    | `/categories/{id}` | Obtiene los detalles de una categoría por ID.        | Todos            |
| **POST**   | `/categories`      | Crea una nueva categoría.                            | Solo Admin       |
| **PUT**    | `/categories/{id}` | Actualiza los datos de una categoría por ID.         | Solo Admin       |
| **DELETE** | `/categories/{id}` | Elimina una categoría por ID.                        | Solo Admin       |

---

### Endpoints para **Usuarios (Users)**

| **Método** | **Endpoint**        | **Descripción**                                      | **Acceso**                |
|------------|---------------------|------------------------------------------------------|---------------------------|
| **GET**    | `/users`            | Obtiene la lista de todos los usuarios registrados.  | Solo Admin                |
| **GET**    | `/users/{username}` | Obtiene los detalles de un usuario específico por username.| Cliente (solo su info) o Admin |
| **POST**   | `/users/register`   | Registra un nuevo usuario.                           | Todos                    |
| **POST**   | `/users/login`      | Inicia sesión y genera un token JWT.                 | Todos                    |
| **PUT**    | `/users/{username}`       | Actualiza los datos de un usuario específico por username. | Cliente (solo su info) o Admin |
| **DELETE** | `/users/{username}`       | Elimina un usuario por username.                           | Cliente (solo su info) o Admin                |

---

### Endpoints para **Préstamos (Loans)**

| **Método** | **Endpoint**       | **Descripción**                                      | **Acceso**                |
|------------|--------------------|------------------------------------------------------|---------------------------|
| **GET**    | `/loans`           | Obtiene todos los préstamos registrados.            | Solo Admin                |
| **GET**    | `/loans/{id}`      | Obtiene los préstamos realizados por el usuario autenticado.| Cliente (solo su info) o Admin |
| **POST**   | `/loans`           | Registra un nuevo préstamo.                          | Solo Admin                |
| **PUT**    | `/loans/{id}`      | Actualiza los datos de un préstamo por ID.           | Solo Admin                |
| **DELETE** | `/loans/{id}`      | Elimina un préstamo por ID.                          | Solo Admin                |

---

### Endpoints para **Reservas (Reservations)**

| **Método** | **Endpoint**       | **Descripción**                                      | **Acceso**                |
|------------|--------------------|------------------------------------------------------|---------------------------|
| **GET**    | `/reservations`    | Obtiene todas las reservas registradas.             | Solo Admin                |
| **GET**    | `/reservations/{id}`| Obtiene las reservas realizadas por el usuario autenticado.| Cliente (solo su info) o Admin |
| **POST**   | `/reservations`    | Registra una nueva reserva.                          | Solo Admin |
| **PUT**    | `/reservations/{id}`| Actualiza los datos de una reserva por ID.           | Solo Admin                |
| **DELETE** | `/reservations/{id}`| Elimina una reserva por ID.                          | Solo Admin                |

---

### Endpoints para **Comentarios (Comments)**

| **Método** | **Endpoint**  | **Descripción**                                      | **Acceso**                     |
|------------|---------------|------------------------------------------------------|--------------------------------|
| **GET**    | `/comments`   | Obtiene todos los comentarios de los libros.         | Todos                          |
| **GET**    | `/comments/{id}` | Obtiene un comentario por ID.         | Todos                          |
| **POST**   | `/comments`        | Crea un nuevo comentario para un libro.              | Todos                          |
| **PUT**    | `/comments/{id}`   | Actualiza un comentario por ID.                      | Cliente (solo su info) o Admin |
| **DELETE** | `/comments/{id}`   | Elimina un comentario por ID.                        | Cliente (solo su info) o Admin |

---

## Lógica de Negocio

La lógica de negocio está centrada en los siguientes aspectos clave:

- **Autenticación y Autorización**: Utilizamos **JWT** para autenticar y autorizar a los usuarios. Los clientes deben estar autenticados para acceder a sus propios datos. Los administradores tienen permisos para gestionar todos los recursos del sistema.

- **Validaciones en los campos**: Los campos de la API se validan para asegurarse de que contengan datos válidos y no sean nulos. Estas validaciones garantizan la integridad de los datos almacenados en la base de datos.

## Validación de Campos

| **Entidad**          | **Campo**     | **Regla de Validación**                                                                               |
|----------------------|---------------|-------------------------------------------------------------------------------------------------------|
| **Author**           | `name`        | Debe ser único, no estar vacío.                                                                       |
|                      | `nationality` | No estar vacío.                                                                                       |
| **Book**             | `title`       | No puede estar vacío, debe tener entre 1 y 100 caracteres.                                            |
|                      | `isbn`        | Debe tener exactamente 13 caracteres, ser único y no estar vacío.                                     |
|                      | `author`      | Debe referenciar un autor existente (validación en base a la clave foránea).                          |
|                      | `category`    | Debe referenciar una categoría existente (validación en base a la clave foránea).                     |
| **Category**         | `name`        | Debe ser único, no estar vacío.                                                                       |
| **User**             | `email`       | Debe tener un formato válido y ser único (`regex: ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$`). |
|                      | `password`    | Debe tener al menos 6 caracteres.                                                                     |
|                      | `name`        | Debe ser único, no estar vacio.                                                                       |
|                      | `role`        | Debe ser uno de los valores permitidos, ADMIN o USER.                                                 |CLIENT)$`).                                                         |
| **Loan/Reservation** | `date`        | No puede ser en el pasado; debe tener formato válido de fecha.                                        |
|                      | `user`        | Debe referenciar un usuario existente (validación en base a la clave foránea).                        |
|                      | `book`        | Debe referenciar un libro existente (validación en base a la clave foránea).                          |
|                      | `status`      | No estar vacio y ser PENDING, COMPLETE or CANCELLED.                                                  |
| **Comment**          | `content`     | No puede superar los 500 caracteres y no debe estar vacío.                                            |
|                      | `book`        | Debe referenciar un libro existente (validación en base a la clave foránea).                          |
|                      | `user`        | Debe referenciar un usuario existente (validación en base a la clave foránea).                        |

---

## Excepciones y Códigos de Respuesta

| **Código** | **Excepción**         | **Descripción**                                                                 |
|------------|-----------------------|---------------------------------------------------------------------------------|
| **400**    | `BadRequest`          | Ocurre cuando se envían datos inválidos o incompletos en la solicitud.         |
| **404**    | `NotFound`            | Se lanza cuando un recurso solicitado no existe.                               |
| **409**    | `DuplicateEntry`      | Aparece cuando se intenta registrar un recurso con un valor único ya existente.|
| **500**    | `InternalError`       | Se produce cuando ocurre un error inesperado en el servidor.                   |

### Ejemplos de Uso
1. **400 - BadRequest**:
   - Intentar crear un autor sin nombre.
   - Registrar un libro con un ISBN no válido.

2. **404 - NotFound**:
   - Buscar un libro que no existe en la base de datos.
   - Intentar actualizar un usuario con un ID inexistente.

3. **409 - DuplicateEntry**:
   - Intentar registrar un autor con un nombre que ya existe.
   - Registrar un libro con un ISBN ya utilizado.

4. **500 - InternalError**:
   - Ocurre cuando un error no controlado afecta al funcionamiento de la API.

---

## Requisitos

- **Java 17** o superior.
- **Spring Boot** 2.7.x o superior.
- **JWT** para autenticación y autorización.
- **Hibernate** para la gestión de la persistencia.
- **MySQL/PostgreSQL** como sistema de base de datos.
- **Postman** o herramientas similares para probar la API.

---
