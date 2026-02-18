
package com.example.medical.web;

import com.example.medical.dto.medical.*;
import com.example.medical.service.MedicalRecordService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {

  private final MedicalRecordService service;

  public MedicalRecordController(MedicalRecordService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<MedicalRecordResponse> create(@Valid @RequestBody MedicalRecordCreateRequest req) {
    MedicalRecordResponse created = service.create(req);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/<built-in function id>")
      .buildAndExpand(created.id()).toUri();
    return ResponseEntity.created(location).body(created);
  }

  @GetMapping("/<built-in function id>")
  public ResponseEntity<MedicalRecordResponse> get(@PathVariable Long id) {
    return ResponseEntity.ok(service.getById(id));
  }
}
