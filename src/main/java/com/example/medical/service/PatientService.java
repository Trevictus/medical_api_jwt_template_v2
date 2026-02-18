
package com.example.medical.service;

import com.example.medical.dto.patient.PatientCreateRequest;
import com.example.medical.dto.patient.PatientResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {
  PatientResponse create(PatientCreateRequest req);
  PatientResponse getById(Long id);
  Page<PatientResponse> list(Pageable pageable);
  PatientResponse update(Long id, PatientCreateRequest req);
  void delete(Long id);
}
