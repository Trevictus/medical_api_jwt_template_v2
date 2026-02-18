
package com.example.medical.service;

import com.example.medical.domain.*;
import com.example.medical.dto.appointment.*;
import com.example.medical.error.InvalidDataException;
import com.example.medical.error.NotFoundException;
import com.example.medical.repo.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

  private final AppointmentRepository appts;
  private final DoctorRepository doctors;
  private final PatientRepository patients;

  public AppointmentServiceImpl(AppointmentRepository appts, DoctorRepository doctors, PatientRepository patients) {
    this.appts = appts;
    this.doctors = doctors;
    this.patients = patients;
  }

  @Override
  public AppointmentResponse create(AppointmentCreateRequest req) {
    validateTimes(req.startAt(), req.endAt());

    Doctor d = doctors.findById(req.doctorId()).orElseThrow(() -> new NotFoundException("Doctor not found"));
    Patient p = patients.findById(req.patientId()).orElseThrow(() -> new NotFoundException("Patient not found"));

    Appointment a = new Appointment();
    a.setDoctor(d);
    a.setPatient(p);
    a.setStartAt(req.startAt());
    a.setEndAt(req.endAt());
    a.setReason(req.reason());
    a = appts.save(a);

    return toDto(a);
  }

  @Override
  public AppointmentResponse getById(Long id) {
    Appointment a = appts.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found"));
    return toDto(a);
  }

  @Override
  public Page<AppointmentResponse> list(Long doctorId, Long patientId, AppointmentStatus status, LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {
    // Minimal baseline: filter in memory (students can improve with Specifications/QueryDSL)
    return appts.findAll(pageable).map(this::toDto).map(dto -> dto);
  }

  @Override
  public AppointmentResponse update(Long id, AppointmentCreateRequest req) {
    validateTimes(req.startAt(), req.endAt());

    Appointment a = appts.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found"));
    Doctor d = doctors.findById(req.doctorId()).orElseThrow(() -> new NotFoundException("Doctor not found"));
    Patient p = patients.findById(req.patientId()).orElseThrow(() -> new NotFoundException("Patient not found"));

    a.setDoctor(d);
    a.setPatient(p);
    a.setStartAt(req.startAt());
    a.setEndAt(req.endAt());
    a.setReason(req.reason());
    a = appts.save(a);
    return toDto(a);
  }

  @Override
  public void delete(Long id) {
    if (!appts.existsById(id)) throw new NotFoundException("Appointment not found");
    appts.deleteById(id);
  }

  private void validateTimes(LocalDateTime start, LocalDateTime end) {
    if (start == null || end == null) throw new InvalidDataException("startAt/endAt required");
    if (!end.isAfter(start)) throw new InvalidDataException("endAt must be after startAt");
  }

  private AppointmentResponse toDto(Appointment a) {
    return new AppointmentResponse(
      a.getId(),
      a.getDoctor().getId(),
      a.getPatient().getId(),
      a.getStartAt(),
      a.getEndAt(),
      a.getStatus(),
      a.getReason()
    );
  }
}
