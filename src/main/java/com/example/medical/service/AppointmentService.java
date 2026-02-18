
package com.example.medical.service;

import com.example.medical.dto.appointment.AppointmentCreateRequest;
import com.example.medical.dto.appointment.AppointmentResponse;
import com.example.medical.domain.AppointmentStatus;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {
  AppointmentResponse create(AppointmentCreateRequest req);
  AppointmentResponse getById(Long id);
  Page<AppointmentResponse> list(Long doctorId, Long patientId, AppointmentStatus status, LocalDate dateFrom, LocalDate dateTo, Pageable pageable);
  AppointmentResponse update(Long id, AppointmentCreateRequest req);
  void delete(Long id);
}
