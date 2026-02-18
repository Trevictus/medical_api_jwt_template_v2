# Lista de tests mínimos (Sistema Médico – Spring Boot + JWT)

## Reglas generales

* Los tests deben ejecutarse con `mvn test` o `./gradlew test` sin pasos manuales.
* Se permiten **H2** para tests.
* Si usan JWT, se recomienda `spring-security-test` para simular usuarios/tokens.
* No se exige una estructura concreta: lo que importa es que los tests verifiquen el comportamiento.

---

## A) Tests de Controlador (MockMvc) – mínimos obligatorios

**Objetivo**: contrato REST + validación + errores + seguridad.

> Recomendación: `@WebMvcTest` para controllers + mock de servicios.
> Alternativa válida: `@SpringBootTest` + `@AutoConfigureMockMvc`.

### A1. POST /patients → 201 y Location correcto

* **Dado**: DTO válido de paciente
* **Cuando**: POST
* **Entonces**:

  * status **201**
  * header `Location` termina en `/patients/{id}`
  * response body es DTO (no entidad) y contiene el `id`

### A2. POST /patients DTO inválido → 400 con detalle

* **Dado**: DTO con campo obligatorio vacío (p.ej. `firstName=""`)
* **Entonces**:

  * status **400**
  * body contiene estructura de error homogénea
  * incluye (si lo implementan) lista de errores de campo

### A3. GET /patients/{id} inexistente → 404 con ErrorResponse

* **Entonces**:

  * status **404**
  * body `{status, message, path, timestamp...}`

### A4. DELETE /patients/{id} existente → 204

* **Entonces**:

  * status **204**
  * body vacío

### A5. PUT /patients/{id} existente → 200 (o 204) y actualiza

* **Entonces**:

  * status **200** (o **204**, pero consistente en toda la API)
  * response DTO actualizado (si 200)

---

## B) Tests de JWT y roles (MockMvc) – mínimos obligatorios (JWT obligatorio)

**Objetivo**: autenticación + autorización real por rol.

### B1. POST /auth/login credenciales válidas → 200 y devuelve token

* **Entonces**:

  * status **200**
  * JSON tiene `token` (y opcionalmente `tokenType`, `expiresIn`)

### B2. Acceso sin token a endpoint protegido → 401

* Ejemplo: `GET /patients`
* **Entonces**:

  * status **401**

### B3. Acceso con rol incorrecto → 403

* Ejemplo recomendado:

  * `PACIENTE` intentando `DELETE /patients/{id}`
* **Entonces**:

  * status **403**

### B4. Acceso con rol permitido → 200/204

* Ejemplo recomendado:

  * `RECEPCIONISTA` crea cita o crea paciente
* **Entonces**:

  * status correcto y operación OK


---

## C) Tests de Servicio (JUnit + Mockito) – mínimos recomendados

**Objetivo**: lógica de negocio + excepciones.

> Recomendación: tests puros con mocks de repositorio.

### C1. Crear cita con doctor inexistente → lanza NotFound (404)

* **Dado**: repo doctor devuelve empty
* **Entonces**: se lanza excepción custom `DoctorNotFoundException` (o equivalente)

### C2. Crear cita con paciente inexistente → lanza NotFound

* Misma idea

### C3. Crear cita con rango horario inválido → InvalidData (400)

* `end <= start` debe fallar

### C4. Cambiar estado de cita: transición inválida → InvalidData

* Ejemplo: COMPLETADA → PROGRAMADA (si definís reglas)
* Debe lanzar excepción

### C5. Regla de solape (si implementada) → Conflict (409)

* **Dado**: existe cita que solapa para el mismo médico
* **Entonces**: excepción conflict

> Si no implementas solape, puedes sustituirla por otra regla equivalente (p.ej. “cita debe ser dentro de horario laboral”) pero debe existir **al menos una regla de negocio verificable**.

---

## D) Tests de Repositorio (DataJpaTest) – mínimos recomendados

**Objetivo**: queries para filtros (especialmente citas).

### D1. Buscar citas por doctorId devuelve solo las suyas

* Inserta datos con 2 doctores
* Query devuelve solo las del doctor A

### D2. Buscar citas por rango de fechas

* Inserta varias citas
* Query `between from/to` devuelve las correctas

### D3. Buscar por doctorId + estado (filtro combinable)

* Inserta estados mezclados
* Query devuelve solo las correctas

### D4. Restricción unique (si la definen) provoca excepción

* Ejemplo: `email` en Usuario o `numColegiado` en Médico
* Guardar duplicado debe fallar (DataIntegrityViolationException o similar)

---

## E) Tests de Integración (SpringBootTest) – mínimos para 10

**Objetivo**: flujo completo real con seguridad y BD.

### E1. Flujo end-to-end (con JWT):

1. Login RECEPCIONISTA → token
2. POST /patients → 201 + id
3. POST /appointments → 201 + id
4. GET /appointments?patientId=… → 200 y aparece
5. DELETE /appointments/{id} → 204

**Criterio**: todo en un solo test o en varios encadenados, pero sin mocks de servicio/repositorio.

### E2. Verificación de autorización real en integración

* `PACIENTE` no puede `DELETE /patients/{id}` → 403

---

# Mínimo exigible por tramo 

### Para llegar a 9 (mínimo de tests)

* **Obligatorios**: A1–A5 (5) + B1–B4 (4) = **9**
* * al menos **6 adicionales** entre C y D = **15** total

### Para 10

* Todo lo anterior + **E1 y E2** (integración)

---

## Nota práctica (para no perderse)


* Haced `@SpringBootTest + MockMvc` para casi todo (más simple mentalmente).
* Y solo `@WebMvcTest` si se domina bien mocks.
* Para JWT, se puede:

  * usar `spring-security-test` con `@WithMockUser` si su configuración lo permite, o
  * hacer login real en test de integración y usar el token.

