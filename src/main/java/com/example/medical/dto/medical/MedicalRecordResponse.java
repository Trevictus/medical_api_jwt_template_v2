
package com.example.medical.dto.medical;

import java.time.Instant;

public record MedicalRecordResponse(
  Long id,
  Long appointmentId,
  Long doctorId,
  String notes,
  Instant createdAt
) {}
