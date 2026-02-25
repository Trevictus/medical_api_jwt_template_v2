# ERD — Sistema Médico (base)

Este diagrama es una **plantilla**. Puedes modificar entidades/campos, pero deben mantenerse las relaciones mínimas.

## Entidades mínimas
- AppUser (usuarios del sistema, con rol)
- Patient
- Doctor
- Specialty
- Appointment
- MedicalRecord

## Relaciones mínimas
- AppUser 1..0/1 Patient (opcional)
- AppUser 1..1 Doctor (si el usuario es médico)
- Doctor N..M Specialty (tabla puente doctor_specialties)
- Appointment N..1 Doctor
- Appointment N..1 Patient
- MedicalRecord 1..1 Appointment (unique)
- MedicalRecord N..1 Doctor

## Mermaid (ERD)
```mermaid
erDiagram
  APP_USERS ||--o| PATIENTS : "user_id (optional)"
  APP_USERS ||--|| DOCTORS : "user_id (unique)"
  DOCTORS }o--o{ SPECIALTIES : doctor_specialties
  DOCTORS ||--o{ APPOINTMENTS : doctor_id
  PATIENTS ||--o{ APPOINTMENTS : patient_id
  APPOINTMENTS ||--|| MEDICAL_RECORDS : appointment_id (unique)
  DOCTORS ||--o{ MEDICAL_RECORDS : doctor_id

  APP_USERS {
    bigint id PK
    string email UK
    string password_hash
    string role
    boolean active
  }

  PATIENTS {
    bigint id PK
    bigint user_id UK "nullable"
    string dni UK "nullable"
    string first_name
    string last_name
    string phone
    boolean active
  }

  DOCTORS {
    bigint id PK
    bigint user_id UK
    string first_name
    string last_name
    string license_number UK
  }

  SPECIALTIES {
    bigint id PK
    string code UK
    string name
    boolean active
  }

  APPOINTMENTS {
    bigint id PK
    bigint doctor_id FK
    bigint patient_id FK
    datetime start_at
    datetime end_at
    string status
    string reason
  }

  MEDICAL_RECORDS {
    bigint id PK
    bigint appointment_id FK UK
    bigint doctor_id FK
    string notes
    datetime created_at
  }
```

## Notas para la entrega

### Reglas de negocio

#### Estados de citas (AppointmentStatus)
Las citas pueden tener los siguientes estados:
- `PROGRAMADA` - Estado inicial al crear una cita
- `CANCELADA` - Cita cancelada
- `COMPLETADA` - Cita finalizada correctamente
- `NO_PRESENTADO` - El paciente no se presentó

#### Transiciones de estado permitidas
```
PROGRAMADA → COMPLETADA (cita realizada)
PROGRAMADA → CANCELADA (cita cancelada)
PROGRAMADA → NO_PRESENTADO (paciente ausente)
```

#### Validaciones de citas
- `startAt` y `endAt` son obligatorios
- `endAt` debe ser posterior a `startAt`
- El doctor y paciente deben existir en el sistema


### Restricciones por rol en endpoints

| Endpoint | Roles permitidos |
|----------|------------------|
| `POST /auth/**` | Público (sin autenticación) |
| `/actuator/**` | Público (sin autenticación) |
| `/h2-console/**` | Público (sin autenticación) |
| `/patients/**` | `ADMIN`, `RECEPCIONISTA` |
| `/appointments/**` | `ADMIN`, `RECEPCIONISTA`, `MEDICO`, `PACIENTE` |
| `/medical-records/**` | `ADMIN`, `MEDICO` |

### Roles del sistema
- `ADMIN` - Acceso total al sistema
- `RECEPCIONISTA` - Gestión de pacientes y citas
- `MEDICO` - Gestión de citas y registros médicos
- `PACIENTE` - Solo consulta de sus propias citas
