
package com.example.medical.service;

import com.example.medical.domain.Doctor;
import com.example.medical.dto.appointment.AppointmentCreateRequest;
import com.example.medical.error.InvalidDataException;
import com.example.medical.error.NotFoundException;
import com.example.medical.repo.AppointmentRepository;
import com.example.medical.repo.DoctorRepository;
import com.example.medical.repo.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {
  @Mock
  AppointmentRepository appts;
  @Mock
  DoctorRepository doctors;
  @Mock
  PatientRepository patients;


  AppointmentServiceImpl service;

  @BeforeEach
  void setUp() {
    service = new AppointmentServiceImpl(appts, doctors, patients);
  }

  @Test
  void create_doctorNotFound_throwsNotFoundException(){
    LocalDateTime start = LocalDateTime.now().plusHours(1);
    LocalDateTime end = start.plusHours(1);
    AppointmentCreateRequest req = new AppointmentCreateRequest(97L, 1L, start, end, "consulta");

    when(doctors.findById(97L)).thenReturn(java.util.Optional.empty());

    NotFoundException ex = assertThrows(NotFoundException.class, () -> service.create(req));
    assertEquals("Doctor not found", ex.getMessage());
  }

  @Test
  void create_patientNotFound_throwsNotFoundException() {
    LocalDateTime start = LocalDateTime.now().plusDays(1);
    LocalDateTime end = start.plusHours(1);
    AppointmentCreateRequest req = new AppointmentCreateRequest(1L, 99L, start, end, "consulta");

    Doctor mockDoctor = mock(Doctor.class);
    when(doctors.findById(1L)).thenReturn(Optional.of(mockDoctor));
    when(patients.findById(999L)).thenReturn(Optional.empty());

    NotFoundException ex = assertThrows(NotFoundException.class, () -> service.create(req));
    assertEquals("Patient not found", ex.getMessage());
  }
}
