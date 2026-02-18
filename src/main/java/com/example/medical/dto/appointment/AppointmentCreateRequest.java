
package com.example.medical.dto.appointment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record AppointmentCreateRequest(
  @NotNull Long doctorId,
  @NotNull Long patientId,
  @NotNull LocalDateTime startAt,
  @NotNull LocalDateTime endAt,
  @Size(max=500) String reason
) {}
