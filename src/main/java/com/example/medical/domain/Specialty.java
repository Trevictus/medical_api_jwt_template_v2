
package com.example.medical.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "specialties", uniqueConstraints = {
  @UniqueConstraint(name = "uk_specialty_code", columnNames = "code")
})
public class Specialty {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String code;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private boolean active = true;

  protected Specialty() {}

  public Long getId() { return id; }
  public String getCode() { return code; }
  public String getName() { return name; }
  public boolean isActive() { return active; }

  public void setCode(String code) { this.code = code; }
  public void setName(String name) { this.name = name; }
  public void setActive(boolean active) { this.active = active; }
}
