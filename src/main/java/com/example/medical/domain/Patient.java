
package com.example.medical.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "patients", uniqueConstraints = {
  @UniqueConstraint(name = "uk_patient_dni", columnNames = "dni")
})
public class Patient {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id", unique = true)
  private AppUser user; // optional

  @Column
  private String dni;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column
  private String phone;

  @Column(nullable = false)
  private boolean active = true;

  public Patient() {}

  public Long getId() { return id; }
  public AppUser getUser() { return user; }
  public String getDni() { return dni; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public String getPhone() { return phone; }
  public boolean isActive() { return active; }

  public void setUser(AppUser user) { this.user = user; }
  public void setDni(String dni) { this.dni = dni; }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public void setPhone(String phone) { this.phone = phone; }
  public void setActive(boolean active) { this.active = active; }
}
