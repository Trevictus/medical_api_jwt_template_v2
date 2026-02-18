# Medical API (Spring Boot) — Template with JWT + Tests

## Requirements
- Java 21
- Maven 3.9+

## Run
```bash
mvn spring-boot:run
```

## H2 Console
- http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:medicaldb`

## Default users (seeded)
- admin@example.com / Admin1234!  (ROLE_ADMIN)
- recep@example.com / Recep1234!  (ROLE_RECEPCIONISTA)
- doc@example.com / Doc1234!      (ROLE_MEDICO)
- pat@example.com / Pat1234!      (ROLE_PACIENTE)

## Login (JWT)
POST `/auth/login`
```json
{ "email": "admin@example.com", "password": "Admin1234!" }
```

Response:
```json
{ "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", "tokenType": "Bearer" }
```

Use:
`Authorization: Bearer <token>`

## Endpoints (baseline)
- `/patients` CRUD
- `/doctors` (read/create baseline)
- `/appointments` CRUD + filters (baseline)
- `/medical-records` create/read baseline

## Tests
```bash
mvn test
```

The project includes placeholders for the minimum tests checklist (controller, service, repo, jwt/security, integration).


## Postman
- Collection: `postman/Medical_API_JWT_Template.postman_collection.json`
- Environment: `postman/Medical_API_Local.postman_environment.json`

## ERD
- `docs/ERD.md`
