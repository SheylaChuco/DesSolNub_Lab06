# SecureDocs — Sistema de Gestión de Documentos con RBAC + ABAC

Sistema de control de acceso para documentos empresariales de TechCorp S.A.,
implementando autorización combinada mediante **RBAC** (Role-Based Access Control)
y **ABAC** (Attribute-Based Access Control).

## Tecnologías

- **Backend:** Spring Boot 3, Spring Security, JWT, JPA/Hibernate
- **Frontend:** React (Vite), React Router, Axios
- **Base de datos:** MySQL 8

## Requisitos previos

- Java 17+
- Node.js 18+
- MySQL 8

## Instalación

### 1. Base de datos

```sql
CREATE DATABASE securedocs_db;
```

### 2. Backend

```bash
cd backend
```

Edita `src/main/resources/application.properties` con tus credenciales de MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/securedocs_db
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

Levanta el servidor:

```bash
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080`. Al iniciar por primera vez, el
`DataSeeder` carga automáticamente los roles, permisos, departamentos y usuarios
de prueba.

### 3. Frontend

```bash
cd frontend
npm install
npm run dev
```

La aplicación queda disponible en `http://localhost:5173`.

## Usuarios de prueba

| Correo | Password | Rol | Departamento |
|---|---|---|---|
| carlos.ruiz@techcorp.com | 123456 | SUPERVISOR | Finanzas |
| ana.torres@techcorp.com | 123456 | EMPLEADO | RRHH |
| luis.gomez@techcorp.com | 123456 | GERENTE | Finanzas |
| maria.paz@techcorp.com | 123456 | AUDITOR | Finanzas |
| rosa.diaz@techcorp.com | 123456 | EMPLEADO (suspendido) | RRHH |
| pedro.ext@cliente.com | 123456 | INVITADO | — |
| admin@techcorp.com | 123456 | ADMINISTRADOR | — |

## Documentación adicional

- [Diagrama de arquitectura](docs/arquitectura.md)
- [Modelo de base de datos](docs/modelo-bd.md)
- [Matriz de roles y permisos (RBAC)](docs/matriz-rbac.md)
- [Matriz de políticas (ABAC)](docs/matriz-abac.md)


## Video de demostración

📹 [Ver demostración en YouTube](https://www.youtube.com/watch?v=vTSSgvPsTJE&t=210s)

## Autor

Sheyla Chuco — TECSUP, Diseño y Desarrollo de Software