
package com.example.medical.service;

import com.example.medical.domain.AppUser;
import com.example.medical.error.NotFoundException;
import com.example.medical.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

  private final UserRepository users;
  private final PasswordEncoder encoder;

  public UserAuthService(UserRepository users, PasswordEncoder encoder) {
    this.users = users;
    this.encoder = encoder;
  }

  public AppUser authenticate(String email, String rawPassword) {
    AppUser user = users.findByEmail(email)
      .orElseThrow(() -> new NotFoundException("User not found"));
    if (!user.isActive()) throw new NotFoundException("User not active");
    if (!encoder.matches(rawPassword, user.getPasswordHash())) {
      throw new NotFoundException("Invalid credentials");
    }
    return user;
  }
}
