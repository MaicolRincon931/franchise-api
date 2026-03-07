# Franchise API - Prueba Técnica

Esta es una API robusta desarrollada con **Spring Boot 3.4.1** y **Java 17**, siguiendo los principios de **Arquitectura Limpia (Clean Architecture)** y **Programación Reactiva**.

##  Tecnologías Utilizadas

- **Java 17** & **Spring Boot 3.4.1**
- **Spring WebFlux**: Programación reactiva y no bloqueante.
- **Spring Data MongoDB Reactive**: Persistencia en MongoDB Atlas.
- **Springdoc OpenAPI (Swagger)**: Documentación interactiva de la API.
- **JUnit 5 & Mockito**: Pruebas unitarias de alta cobertura.
- **Docker & Docker Compose**: Contenerización y orquestación.
- **Terraform**: Infraestructura como Código (IaC).

##  Arquitectura

El proyecto sigue una estructura de **Clean Architecture** para asegurar el desacoplamiento y la mantenibilidad:

1.  **Domain**: Entidades puras, excepciones de dominio y contratos (puertos) del repositorio.
2.  **Application**: Casos de uso específicos que implementan la lógica de negocio.
3.  **Infrastructure**: Implementación de adaptadores (MongoDB), configuración (OpenAPI) y seguridad.
4.  **Presentation**: Controladores REST y DTOs con validaciones.

##  Instalación y Ejecución

### Requisitos Previos
- Docker y Docker Compose
- Java 17 (opcional, si se ejecuta localmente sin Docker)
- Maven 3.8+ (opcional)

### Ejecución con Docker (Recomendado)
Simplemente ejecuta el siguiente comando en la raíz del proyecto:

```powershell
docker-compose up --build
```
La API estará disponible en `http://localhost:8080`.

### Ejecución Local (Maven)
```powershell
./mvnw spring-boot:run
```

## Documentación de la API (Swagger)

Una vez que la aplicación esté corriendo, puedes explorar y probar los endpoints en:
 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

##  Pruebas Unitarias
Para ejecutar los tests unitarios:
```powershell
./mvnw test
```

##  Infraestructura como Código (IaC)
En la carpeta `/terraform` se incluye una configuración ejemplo para provisionar los recursos de MongoDB Atlas utilizando Terraform.

---
Desarrollado como parte de la evaluación técnica para el rol de **Backend Developer**.
