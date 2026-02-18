
package com.example.medical.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctors", uniqueConstraints = {
  @UniqueConstraint(name = "uk_doctor_license", columnNames = "licenseNumber")
})
public class Doctor {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private AppUser user;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String licenseNumber;

  @ManyToMany
  @JoinTable(
    name = "doctor_specialties",
    joinColumns = @JoinColumn(name = "doctor_id"),
    inverseJoinColumns = @JoinColumn(name = "specialty_id")
  )
  private Set<Specialty> specialties = new HashSet<>();

  protected Doctor() {}

  public Long getId() { return id; }
  public AppUser getUser() { return user; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public String getLicenseNumber() { return licenseNumber; }
  public Set<Specialty> getSpecialties() { return specialties; }

  public void setUser(AppUser user) { this.user = user; }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
}
