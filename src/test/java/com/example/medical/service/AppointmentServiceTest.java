
package com.example.medical.service;

import com.example.medical.dto.appointment.AppointmentCreateRequest;
import com.example.medical.error.InvalidDataException;
import com.example.medical.repo.AppointmentRepository;
import com.example.medical.repo.DoctorRepository;
import com.example.medical.repo.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {

  @Test
  void create_invalidTimes_throwsInvalidData() {
    var appts = Mockito.mock(AppointmentRepository.class);
    var doctors = Mockito.mock(DoctorRepository.class);
    var patients = Mockito.mock(PatientRepository.class);

    var svc = new AppointmentServiceImpl(appts, doctors, patients);

    var req = new AppointmentCreateRequest(1L, 1L, LocalDateTime.now(), LocalDateTime.now().minusMinutes(1), "Reason");
    assertThrows(InvalidDataException.class, () -> svc.create(req));
  }
}
