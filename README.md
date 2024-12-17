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

| Atributo       | Tipo          | Descripción                                               |
|----------------|---------------|-----------------------------------------------------------|
| **ID**         | Long          | Clave primaria, autoincremental.                          |
| **Nombre**     | VARCHAR(255)  | Primer nombre del usuario (no nulo).                      |
| **Apellido**   | VARCHAR(255)  | Apellido del usuario (no nulo).                           |
| **Email**      | VARCHAR(255)  | Dirección de correo única (no nula).                      |
| **Password**   | VARCHAR(255)  | Contraseña en formato seguro.                             |
| **Rol**        | ENUM(`ADMIN`, `CLIENT`) | Rol del usuario en el sistema.                  |

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

| **Método** | **Endpoint**       | **Descripción**                                      | **Acceso**                |
|------------|--------------------|------------------------------------------------------|---------------------------|
| **GET**    | `/users`           | Obtiene la lista de todos los usuarios registrados.  | Solo Admin                |
| **GET**    | `/users/{id}`      | Obtiene los detalles de un usuario específico por ID.| Cliente (solo su info) o Admin |
| **POST**   | `/users/register`  | Registra un nuevo usuario.                           | Todos                    |
| **POST**   | `/users/login`     | Inicia sesión y genera un token JWT.                 | Todos                    |
| **PUT**    | `/users/{id}`      | Actualiza los datos de un usuario específico por ID. | Cliente (solo su info) o Admin |
| **DELETE** | `/users/{id}`      | Elimina un usuario por ID.                           | Solo Admin                |

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
| **POST**   | `/reservations`    | Registra una nueva reserva.                          | Cliente (solo su info) o Admin |
| **PUT**    | `/reservations/{id}`| Actualiza los datos de una reserva por ID.           | Solo Admin                |
| **DELETE** | `/reservations/{id}`| Elimina una reserva por ID.                          | Solo Admin                |

---

### Endpoints para **Comentarios (Comments)**

| **Método** | **Endpoint**       | **Descripción**                                      | **Acceso**                |
|------------|--------------------|------------------------------------------------------|---------------------------|
| **GET**    | `/comments`        | Obtiene todos los comentarios de los libros.         | Todos                    |
| **POST**   | `/comments`        | Crea un nuevo comentario para un libro.              | Cliente o Admin           |
| **PUT**    | `/comments/{id}`   | Actualiza un comentario por ID.                      | Cliente (solo su info) o Admin |
| **DELETE** | `/comments/{id}`   | Elimina un comentario por ID.                        | Cliente (solo su info) o Admin |


---
