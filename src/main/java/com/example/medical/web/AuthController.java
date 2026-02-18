
package com.example.medical.web;

import com.example.medical.dto.auth.*;
import com.example.medical.security.JwtService;
import com.example.medical.service.UserAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserAuthService auth;
  private final JwtService jwt;

  public AuthController(UserAuthService auth, JwtService jwt) {
    this.auth = auth;
    this.jwt = jwt;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
    var user = auth.authenticate(req.email(), req.password());
    String token = jwt.generateToken(user.getEmail(), user.getRole().name());
    return ResponseEntity.ok(new LoginResponse(token, "Bearer"));
  }
}
