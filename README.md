# Cool Store API

API REST de la tienda de camisetas Cool Studio, hecha con Spring Boot y MySQL.

## Tecnologías
- Java 21, Spring Boot, Spring Data JPA, Maven
- MySQL / MariaDB (XAMPP)

## Cómo ejecutarla
1. Encender MySQL en XAMPP.
2. Ejecutar el script `docs/cool.sql` para crear la base de datos `cool`.
3. Ejecutar `TiendaApplication` desde IntelliJ (puerto 8080).

## Endpoints
| Método | Ruta | Descripción |
|---|---|---|
| GET | /api/categorias | Listar categorías |
| GET | /api/categorias/{id} | Obtener una categoría |
| POST | /api/categorias | Crear categoría |
| PUT | /api/categorias/{id} | Actualizar categoría |
| DELETE | /api/categorias/{id} | Eliminar categoría |
| GET | /api/productos | Listar productos (`?nombre=`) |
| GET | /api/productos/{id} | Obtener un producto |
| POST | /api/productos | Crear producto |
| PUT | /api/productos/{id} | Actualizar producto |
| DELETE | /api/productos/{id} | Eliminar producto |

## Evidencia
- Script de la base de datos: `docs/cool.sql`
- Diagrama entidad-relación: `docs/modelo-er.png`
- Pruebas en Postman: carpeta `docs/`

## Autor
Santiago García Castañeda
