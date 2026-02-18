
package com.example.medical.dto.patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PatientCreateRequest(
  @Size(max=20) String dni,
  @NotBlank @Size(max=100) String firstName,
  @NotBlank @Size(max=100) String lastName,
  @Size(max=30) String phone
) {}
