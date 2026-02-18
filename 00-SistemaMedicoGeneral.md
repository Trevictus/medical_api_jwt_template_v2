# 📄 TAREA GENERAL

## Recuperación y Mejora – API REST Sistema Médico

### (Versión reutilizable para subir nota)

---

## Objetivo

Esta tarea permite **subir nota** mejorando una API REST con **Spring Boot**, aplicando buenas prácticas profesionales:

* REST correcto
* DTOs y validación
* JWT obligatorio
* Testing
* Evidencias de prueba

---

## Dominio funcional (resumen)

Sistema médico con:

* Usuarios
* Pacientes
* Médicos
* Especialidades
* Citas
* Historial médico

Relaciones coherentes entre entidades (ver ERD obligatorio).

---

## Seguridad (OBLIGATORIA)

Debe implementarse **JWT**:

* Endpoint de login
* Generación y validación de token
* Roles:

  * ADMIN
  * RECEPCIONISTA
  * MEDICO
  * PACIENTE

Cada rol solo accede a lo que le corresponde.

---

## Requisitos técnicos obligatorios

### Arquitectura

* Controller / Service / Repository
* Servicios con interfaz
* Inyección por constructor

### DTO y validación

* DTOs en entrada y salida
* Bean Validation
* `@Valid` en controladores

### REST

* POST → 201 + Location
* PUT/PATCH → actualización
* DELETE → 204
* Errores: 400 / 404 / 409

### Errores

* `@RestControllerAdvice`
* JSON homogéneo de error

---

## Paginación y filtros

* `Page<DTO>` en listados
* Filtros combinables en citas

---

## Tests (obligatorios a partir de 9)

* Repositorio
* Servicio
* Controlador
* Seguridad (JWT)
* Integración (para 10)

---

## Evidencias

* README
* ERD (Diagrama de Entidad-Relación)
* Colección Postman(o Insomnia, el propio cliente rest de Intellij u otro cliente REST)
* Repositorio Git

---

## Checklist por tramos (acumulativo)

### ✅ 6

* [ ] Proyecto funcional
* [ ] CRUD básico
* [ ] README

### 🔼 7

* [ ] DTOs
* [ ] `@Valid`
* [ ] REST correcto
* [ ] Handler de errores

### 🔼 8

* [ ] Paginación
* [ ] Filtros
* [ ] Excepciones custom
* [ ] Postman + ERD

### 🔼 9

* [ ] JWT obligatorio
* [ ] Roles
* [ ] ≥ 15 tests

### 🏆 10

* [ ] Tests de integración
* [ ] Tests de seguridad
* [ ] Código excelente

---

# 📊 RÚBRICA DE EVALUACIÓN

## Recuperación y Mejora – API REST Sistema Médico (Spring Boot + JWT)

**Uso interno docente**
**Evaluación acumulativa por tramos**

---

## Principios de corrección

* La nota **solo sube de tramo si TODO el tramo anterior está completo**.
* No se promedian tramos.
* Los bonus **no compensan carencias** en tramos inferiores.
* Si JWT no está correctamente implementado → **máximo 8**, aunque todo lo demás esté bien.

---

## 🟢 TRAMO 6 — Aprobado técnico básico

| Criterio                                     | OK |
| -------------------------------------------- | -- |
| Proyecto Spring Boot funcional               | ☐  |
| Compila y arranca sin errores                | ☐  |
| CRUD básico operativo                        | ☐  |
| Separación Controller / Service / Repository | ☐  |
| README básico de ejecución                   | ☐  |

📌 **Si falla uno → Suspenso**

---

## 🟡 TRAMO 7 — REST y estructura correcta

*(todo lo anterior +)*

| Criterio                                               | OK |
| ------------------------------------------------------ | -- |
| DTOs en TODOS los endpoints                            | ☐  |
| No se devuelven entidades JPA                          | ☐  |
| Validación real (`@Valid` + Bean Validation)           | ☐  |
| Métodos HTTP correctos                                 | ☐  |
| Códigos HTTP correctos                                 | ☐  |
| Inyección por constructor (sin `@Autowired` en campos) | ☐  |
| `@RestControllerAdvice` funcional                      | ☐  |
| ERD correcto y coherente                               | ☐  |

📌 Errores típicos que **bloquean el 7**:

* `@Valid` ausente
* POST para actualizar
* Entidades devueltas “porque funciona”

---

## 🟠 TRAMO 8 — Calidad de API

*(todo lo anterior +)*

| Criterio                                  | OK |
| ----------------------------------------- | -- |
| Excepciones custom (404 / 409 / 400)      | ☐  |
| JSON de error homogéneo                   | ☐  |
| Paginación real (`Page<DTO>`)             | ☐  |
| Filtros combinables en citas              | ☐  |
| POST devuelve `201 + Location` correcto   | ☐  |
| Colección Postman completa (OK + errores) | ☐  |

📌 Si hay paginación “fake” (listas normales) → no cuenta.

* Location incorrecto en POST → no cuenta.
---

## 🔵 TRAMO 9 — Testing serio + JWT obligatorio

*(todo lo anterior +)*

| Criterio                                | OK |
| --------------------------------------- | -- |
| JWT correctamente implementado          | ☐  |
| Autenticación funcional (`/auth/login`) | ☐  |
| Autorización por roles                  | ☐  |
| Tests de repositorio (`@DataJpaTest`)   | ☐  |
| Tests de servicio (Mockito)             | ☐  |
| Tests de controlador (`@WebMvcTest`)    | ☐  |
| **≥ 15 tests totales**                  | ☐  |

📌 JWT incorrecto o incompleto → **máximo 8**

---

## 🏆 TRAMO 10 — Nivel excelente

*(todo lo anterior +)*

| Criterio                                    | OK |
| ------------------------------------------- | -- |
| Tests de integración (`@SpringBootTest`)    | ☐  |
| Tests de seguridad (`spring-security-test`) | ☐  |
| Accesos bien restringidos por rol           | ☐  |
| Manejo correcto de 401 / 403                | ☐  |
| Código limpio y consistente                 | ☐  |
| Documentación clara y profesional           | ☐  |

---

## Observaciones rápidas (para feedback)

* Arquitectura:
* REST:
* Validación:
* JWT:
* Tests:
* Comentario global:

---