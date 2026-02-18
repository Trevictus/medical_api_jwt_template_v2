
package com.example.medical.dto.patient;

public record PatientResponse(
  Long id,
  String dni,
  String firstName,
  String lastName,
  String phone,
  boolean active
) {}
