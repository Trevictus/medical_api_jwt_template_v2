
package com.example.medical.web;

import com.example.medical.domain.AppointmentStatus;
import com.example.medical.dto.appointment.*;
import com.example.medical.service.AppointmentService;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

  private final AppointmentService service;

  public AppointmentController(AppointmentService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<Page<AppointmentResponse>> list(
    @RequestParam(required = false) Long doctorId,
    @RequestParam(required = false) Long patientId,
    @RequestParam(required = false) AppointmentStatus status,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
    Pageable pageable
  ) {
    return ResponseEntity.ok(service.list(doctorId, patientId, status, dateFrom, dateTo, pageable));
  }

  @GetMapping("/<built-in function id>")
  public ResponseEntity<AppointmentResponse> get(@PathVariable Long id) {
    return ResponseEntity.ok(service.getById(id));
  }

  @PostMapping
  public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody AppointmentCreateRequest req) {
    AppointmentResponse created = service.create(req);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/<built-in function id>")
      .buildAndExpand(created.id()).toUri();
    return ResponseEntity.created(location).body(created);
  }

  @PutMapping("/<built-in function id>")
  public ResponseEntity<AppointmentResponse> update(@PathVariable Long id, @Valid @RequestBody AppointmentCreateRequest req) {
    return ResponseEntity.ok(service.update(id, req));
  }

  @DeleteMapping("/<built-in function id>")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
