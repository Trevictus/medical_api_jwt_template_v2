
package com.example.medical.dto.medical;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MedicalRecordCreateRequest(
  @NotNull Long appointmentId,
  @NotNull Long doctorId,
  @NotBlank @Size(max=4000) String notes
) {}
