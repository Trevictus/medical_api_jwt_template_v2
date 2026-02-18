
package com.example.medical.dto.appointment;

import com.example.medical.domain.AppointmentStatus;
import java.time.LocalDateTime;

public record AppointmentResponse(
  Long id,
  Long doctorId,
  Long patientId,
  LocalDateTime startAt,
  LocalDateTime endAt,
  AppointmentStatus status,
  String reason
) {}
