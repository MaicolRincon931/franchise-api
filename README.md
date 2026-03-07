# Franchise API - Prueba Técnica

Esta es una API robusta desarrollada con **Spring Boot 3.4.1** y **Java 17**, siguiendo los principios de **Arquitectura Limpia (Clean Architecture)** y **Programación Reactiva**.

## Tecnologías Utilizadas

- **Java 17** & **Spring Boot 3.4.1**
- **Spring WebFlux**: Programación reactiva y no bloqueante.
- **Spring Data MongoDB Reactive**: Persistencia en MongoDB Atlas.
- **Springdoc OpenAPI (Swagger)**: Documentación interactiva de la API.
- **JUnit 5 & Mockito**: Pruebas unitarias de alta cobertura.
- **Docker & Docker Compose**: Contenerización y orquestación.
- **Terraform**: Infraestructura como Código (IaC).

## Despliegue y Acceso

La aplicación puede ser accedida de dos maneras:

### 1. Entorno Online (Recomendado para pruebas rápidas)
La API está desplegada en **Railway** y es totalmente funcional:
- **Base URL**: `https://franchise-api-production-a413.up.railway.app`
- **Swagger UI**: [https://franchise-api-production-a413.up.railway.app/swagger-ui.html](https://franchise-api-production-a413.up.railway.app/swagger-ui.html)

### 2. Entorno Local (Docker)
Ideal para desarrollo y pruebas de infraestructura:
```powershell
docker-compose up --build
```
- **Base URL**: `http://localhost:8080`
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Endpoints de la API

A continuación se detallan los endpoints principales disponibles:

### Franquicias
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/franchises` | Crear una nueva franquicia |
| `PUT` | `/franchises/{id}/name` | Actualizar el nombre de una franquicia |
| `GET` | `/franchises/{franchiseId}/top-products` | Obtener el producto con más stock por sucursal |

### Sucursales
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/franchises/{franchiseId}/branches` | Agregar sucursal a una franquicia |
| `PUT` | `/branches/{branchId}/name` | Actualizar el nombre de una sucursal |

### Productos
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/branches/{branchId}/products` | Agregar producto a una sucursal |
| `DELETE` | `/branches/{branchId}/products/{productId}` | Eliminar producto de una sucursal |
| `PUT` | `/products/{productId}/stock` | Modificar stock de un producto |
| `PUT` | `/products/{productId}/name` | Actualizar el nombre de un producto |

##  Arquitectura

El proyecto sigue una estructura de **Clean Architecture** para asegurar el desacoplamiento y la mantenibilidad:

1.  **Domain**: Entidades puras, excepciones de dominio y contratos (puertos) del repositorio.
2.  **Application**: Casos de uso específicos que implementan la lógica de negocio.
3.  **Infrastructure**: Implementación de adaptadores (MongoDB), configuración (OpenAPI) y seguridad.
4.  **Presentation**: Controladores REST y DTOs con validaciones.

##  Instalación y Ejecución Local

### Requisitos Previos
- Docker y Docker Compose
- Java 17 (opcional, si se ejecuta localmente sin Docker)
- Maven 3.8+ (opcional)

### Ejecución Local (Maven)
```powershell
./mvnw spring-boot:run
```

## Pruebas Unitarias
Para ejecutar los tests unitarios:
```powershell
./mvnw test
```

##  Infraestructura como Código (IaC)
En la carpeta `/terraform` se incluye una configuración ejemplo para provisionar los recursos de MongoDB Atlas utilizando Terraform.

---
Desarrollado como parte de la evaluación técnica para el rol de **Backend Developer**.
