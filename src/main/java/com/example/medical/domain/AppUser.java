
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

  protected AppUser() {}

  public static AppUser create(String email, String passwordHash, Role role) {
    AppUser u = new AppUser();
    u.email = email;
    u.passwordHash = passwordHash;
    u.role = role;
    return u;
  }

  public Long getId() { return id; }
  public String getEmail() { return email; }
  public String getPasswordHash() { return passwordHash; }
  public Role getRole() { return role; }
  public boolean isActive() { return active; }

  public void setActive(boolean active) { this.active = active; }
}
