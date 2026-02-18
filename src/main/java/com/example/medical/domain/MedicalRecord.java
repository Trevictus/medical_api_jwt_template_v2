
package com.example.medical.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(optional = false)
  @JoinColumn(name = "appointment_id", nullable = false, unique = true)
  private Appointment appointment;

  @ManyToOne(optional = false)
  @JoinColumn(name = "doctor_id", nullable = false)
  private Doctor doctor;

  @Column(nullable = false, length = 4000)
  private String notes;

  @Column(nullable = false)
  private Instant createdAt = Instant.now();

  public MedicalRecord() {}

  public Long getId() { return id; }
  public Appointment getAppointment() { return appointment; }
  public Doctor getDoctor() { return doctor; }
  public String getNotes() { return notes; }
  public Instant getCreatedAt() { return createdAt; }

  public void setAppointment(Appointment appointment) { this.appointment = appointment; }
  public void setDoctor(Doctor doctor) { this.doctor = doctor; }
  public void setNotes(String notes) { this.notes = notes; }
}
