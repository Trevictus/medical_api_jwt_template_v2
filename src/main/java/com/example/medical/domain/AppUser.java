
package com.example.medical.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "app_users", uniqueConstraints = {
  @UniqueConstraint(name = "uk_user_email", columnNames = "email")
})
public class AppUser {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String passwordHash;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @Column(nullable = false)
  private boolean active = true;

  @Column(nullable = false)
  private Instant createdAt = Instant.now();

  public AppUser() {}

  public AppUser(String email, String passwordHash, Role role) {
    this.email = email;
    this.passwordHash = passwordHash;
    this.role = role;
  }

  public Long getId() { return id; }
  public String getEmail() { return email; }
  public String getPasswordHash() { return passwordHash; }
  public Role getRole() { return role; }
  public boolean isActive() { return active; }
  public Instant getCreatedAt() { return createdAt; }

  public void setEmail(String email) { this.email = email; }
  public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
  public void setRole(Role role) { this.role = role; }
  public void setActive(boolean active) { this.active = active; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

  public static AppUser create(String email, String passwordHash, Role role) {
    return new AppUser(email, passwordHash, role);
  }
}

