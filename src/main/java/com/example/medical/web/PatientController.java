
package com.example.medical.web;

import com.example.medical.dto.patient.*;
import com.example.medical.service.PatientService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/patients")
public class PatientController {

  private final PatientService service;

  public PatientController(PatientService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<Page<PatientResponse>> list(Pageable pageable) {
    return ResponseEntity.ok(service.list(pageable));
  }

  @GetMapping("/<built-in function id>")
  public ResponseEntity<PatientResponse> get(@PathVariable Long id) {
    return ResponseEntity.ok(service.getById(id));
  }

  @PostMapping
  public ResponseEntity<PatientResponse> create(@Valid @RequestBody PatientCreateRequest req) {
    PatientResponse created = service.create(req);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/<built-in function id>")
      .buildAndExpand(created.id())
      .toUri();
    return ResponseEntity.created(location).body(created);
  }

  @PutMapping("/<built-in function id>")
  public ResponseEntity<PatientResponse> update(@PathVariable Long id, @Valid @RequestBody PatientCreateRequest req) {
    return ResponseEntity.ok(service.update(id, req));
  }

  @DeleteMapping("/<built-in function id>")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
