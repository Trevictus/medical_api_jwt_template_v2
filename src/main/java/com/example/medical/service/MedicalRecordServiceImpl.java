
package com.example.medical.service;

import com.example.medical.domain.*;
import com.example.medical.dto.medical.*;
import com.example.medical.error.ConflictException;
import com.example.medical.error.NotFoundException;
import com.example.medical.repo.*;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

  private final MedicalRecordRepository records;
  private final AppointmentRepository appts;
  private final DoctorRepository doctors;

  public MedicalRecordServiceImpl(MedicalRecordRepository records, AppointmentRepository appts, DoctorRepository doctors) {
    this.records = records;
    this.appts = appts;
    this.doctors = doctors;
  }

  @Override
  public MedicalRecordResponse create(MedicalRecordCreateRequest req) {
    Appointment a = appts.findById(req.appointmentId()).orElseThrow(() -> new NotFoundException("Appointment not found"));
    Doctor d = doctors.findById(req.doctorId()).orElseThrow(() -> new NotFoundException("Doctor not found"));

    // appointment_id unique constraint for MedicalRecord -> check first for clearer 409
    boolean exists = records.findAll().stream().anyMatch(r -> r.getAppointment().getId().equals(a.getId()));
    if (exists) throw new ConflictException("Medical record already exists for appointment");

    MedicalRecord r = new MedicalRecord();
    r.setAppointment(a);
    r.setDoctor(d);
    r.setNotes(req.notes());
    r = records.save(r);
    return new MedicalRecordResponse(r.getId(), a.getId(), d.getId(), r.getNotes(), r.getCreatedAt());
  }

  @Override
  public MedicalRecordResponse getById(Long id) {
    MedicalRecord r = records.findById(id).orElseThrow(() -> new NotFoundException("Medical record not found"));
    return new MedicalRecordResponse(r.getId(), r.getAppointment().getId(), r.getDoctor().getId(), r.getNotes(), r.getCreatedAt());
  }
}
