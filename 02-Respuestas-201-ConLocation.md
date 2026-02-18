# 🌱 Ejemplo básico: 201 Created + Location

Supongamos que creas un recurso `Alumno` y quieres devolver:

- **HTTP 201 Created**
- **Cabecera Location** apuntando al nuevo recurso: `/alumnos/{id}`
- (Opcional) **El cuerpo con el recurso creado**

## ✔️ Controlador Spring Boot

```java
@PostMapping("/alumnos")
public ResponseEntity<Alumno> crearAlumno(@RequestBody Alumno nuevo) {
    Alumno guardado = alumnoService.save(nuevo);

    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(guardado.getId())
            .toUri();

    return ResponseEntity
            .created(location)   // <-- 201 + Location
            .body(guardado);     // <-- opcional, pero muy habitual
}
```

### ¿Qué hace este código?

- `fromCurrentRequest()` → usa `/alumnos`
- `path("/{id}")` → añade el ID del recurso recién creado
- `created(location)` → genera automáticamente **201 Created** + cabecera **Location: /alumnos/{id}**

---

# 📌 ¿Es obligatorio devolver Location?

**Sí (con matices), cuando creas un recurso nuevo.**  
La especificación HTTP/1.1 **recomienda** que **toda respuesta 201 incluya Location** apuntando al recurso recién creado.

---

# 📍 ¿En qué otros casos es útil devolver Location?

No es obligatorio, pero sí **conveniente** en estos escenarios:

### 1. **Redirecciones tras operaciones asíncronas**
Si creas un recurso que aún no está listo (p. ej., un proceso de larga duración), puedes devolver:

- **202 Accepted**
- `Location: /procesos/{id}` para consultar el estado

### 2. **Creación indirecta**
Cuando una operación POST no crea un recurso directamente, pero **deriva** en uno nuevo. Ejemplo:

- POST `/importar` → crea un lote de importación
- Devuelves `Location: /lotes/{id}`

### 3. **PUT que crea recurso**
Aunque no es lo más común, si un **PUT crea un recurso**, también es correcto devolver:

- **201 Created**
- **Location** del recurso creado

### 4. **Buenas prácticas HATEOAS**
Si usas HATEOAS, puedes incluir enlaces relevantes, aunque no es obligatorio.

---

# 🧩 Resumen rápido

| Operación | Código | ¿Location? | Motivo |
|----------|--------|------------|--------|
| POST crea recurso | **201** | ✔️ Sí | Indicar dónde está el nuevo recurso |
| PUT crea recurso | **201** | ✔️ Sí | Igual que POST |
| POST procesa algo asíncrono | **202** | ✔️ Recomendado | Indicar dónde consultar el estado |
| POST que no crea recurso | 200/204 | ❌ No | No hay recurso nuevo |

---

# 📄 Plantilla: creación de recurso con **201 Created + Location**

## ✔️ Controlador (plantilla genérica)

```java
@PostMapping
public ResponseEntity<T> create(@RequestBody T dto) {

    // 1. Guardar el recurso (servicio o repositorio)
    T saved = service.save(dto);

    // 2. Construir la URI del recurso recién creado
    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()      // /recurso
            .path("/{id}")             // /recurso/{id}
            .buildAndExpand(saved.getId())
            .toUri();

    // 3. Devolver 201 + Location + cuerpo opcional
    return ResponseEntity
            .created(location)
            .body(saved);
}
```

---

# 🧱 Versión parametrizable para copiar/pegar en cualquier proyecto

Sustituye:

- `Recurso` → nombre de tu entidad  
- `RecursoDTO` → tu DTO si lo usas  
- `recursoService` → tu servicio  
- `getId()` → tu método identificador  

```java
@PostMapping
public ResponseEntity<RecursoDTO> create(@RequestBody RecursoDTO dto) {

    RecursoDTO saved = recursoService.create(dto);

    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();

    return ResponseEntity
            .created(location)
            .body(saved);
}
```

---

# 🧩 Plantilla con validación + conversión DTO → entidad

```java
@PostMapping
public ResponseEntity<RecursoDTO> create(@Valid @RequestBody RecursoDTO dto) {

    Recurso entity = mapper.toEntity(dto);
    Recurso saved = service.save(entity);

    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();

    return ResponseEntity
            .created(location)
            .body(mapper.toDTO(saved));
}
```

---

# 📌 Plantilla para PUT que crea recurso (idempotente)

```java
@PutMapping("/{id}")
public ResponseEntity<RecursoDTO> upsert(
        @PathVariable Long id,
        @RequestBody RecursoDTO dto) {

    boolean existed = service.exists(id);
    RecursoDTO saved = service.upsert(id, dto);

    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .build()
            .toUri();

    if (!existed) {
        return ResponseEntity.created(location).body(saved);
    }

    return ResponseEntity.ok(saved);
}
```

---

# 📍 Plantilla para procesos asíncronos (202 + Location)

```java
@PostMapping("/procesar")
public ResponseEntity<Void> iniciarProceso(@RequestBody Datos datos) {

    Long procesoId = procesoService.iniciar(datos);

    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(procesoId)
            .toUri();

    return ResponseEntity.accepted().location(location).build();
}
```

---

# 🎁 Bonus: plantilla para tests (MockMvc)

```java
@Test
void crearRecursoDevuelve201YLocation() throws Exception {

    when(service.save(any())).thenReturn(new Recurso(1L, "dato"));

    mockMvc.perform(post("/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"campo\":\"valor\"}"))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", containsString("/recursos/1")));
}
```



