
package com.example.medical.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "doctor_id", nullable = false)
  private Doctor doctor;

  @ManyToOne(optional = false)
  @JoinColumn(name = "patient_id", nullable = false)
  private Patient patient;

  @Column(nullable = false)
  private LocalDateTime startAt;

  @Column(nullable = false)
  private LocalDateTime endAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AppointmentStatus status = AppointmentStatus.PROGRAMADA;

  @Column
  private String reason;

  public Appointment() {}

  public Long getId() { return id; }
  public Doctor getDoctor() { return doctor; }
  public Patient getPatient() { return patient; }
  public LocalDateTime getStartAt() { return startAt; }
  public LocalDateTime getEndAt() { return endAt; }
  public AppointmentStatus getStatus() { return status; }
  public String getReason() { return reason; }

  public void setDoctor(Doctor doctor) { this.doctor = doctor; }
  public void setPatient(Patient patient) { this.patient = patient; }
  public void setStartAt(LocalDateTime startAt) { this.startAt = startAt; }
  public void setEndAt(LocalDateTime endAt) { this.endAt = endAt; }
  public void setStatus(AppointmentStatus status) { this.status = status; }
  public void setReason(String reason) { this.reason = reason; }
}
