
package com.example.medical.service;

import com.example.medical.domain.Patient;
import com.example.medical.dto.patient.PatientCreateRequest;
import com.example.medical.dto.patient.PatientResponse;
import com.example.medical.error.NotFoundException;
import com.example.medical.repo.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

  private final PatientRepository patients;

  public PatientServiceImpl(PatientRepository patients) {
    this.patients = patients;
  }

  @Override
  public PatientResponse create(PatientCreateRequest req) {
    Patient p = new Patient();
    p.setDni(req.dni());
    p.setFirstName(req.firstName());
    p.setLastName(req.lastName());
    p.setPhone(req.phone());
    p = patients.save(p);
    return toDto(p);
  }

  @Override
  public PatientResponse getById(Long id) {
    Patient p = patients.findById(id).orElseThrow(() -> new NotFoundException("Patient not found"));
    return toDto(p);
  }

  @Override
  public Page<PatientResponse> list(Pageable pageable) {
    return patients.findAll(pageable).map(this::toDto);
  }

  @Override
  public PatientResponse update(Long id, PatientCreateRequest req) {
    Patient p = patients.findById(id).orElseThrow(() -> new NotFoundException("Patient not found"));
    p.setDni(req.dni());
    p.setFirstName(req.firstName());
    p.setLastName(req.lastName());
    p.setPhone(req.phone());
    p = patients.save(p);
    return toDto(p);
  }

  @Override
  public void delete(Long id) {
    if (!patients.existsById(id)) throw new NotFoundException("Patient not found");
    patients.deleteById(id);
  }

  private PatientResponse toDto(Patient p) {
    return new PatientResponse(p.getId(), p.getDni(), p.getFirstName(), p.getLastName(), p.getPhone(), p.isActive());
  }
}
