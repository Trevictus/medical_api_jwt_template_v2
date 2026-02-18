
package com.example.medical.service;

import com.example.medical.dto.medical.MedicalRecordCreateRequest;
import com.example.medical.dto.medical.MedicalRecordResponse;

public interface MedicalRecordService {
  MedicalRecordResponse create(MedicalRecordCreateRequest req);
  MedicalRecordResponse getById(Long id);
}
