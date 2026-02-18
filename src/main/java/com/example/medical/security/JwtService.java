
package com.example.medical.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final JwtProperties props;

  public JwtService(JwtProperties props) {
    this.props = props;
  }

  public String generateToken(String subject, String role) {
    Instant now = Instant.now();
    Instant exp = now.plus(props.getExpirationMinutes(), ChronoUnit.MINUTES);
    return Jwts.builder()
      .setSubject(subject)
      .claim("role", role)
      .setIssuedAt(Date.from(now))
      .setExpiration(Date.from(exp))
      .signWith(signingKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  public Jws<Claims> parse(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(signingKey())
      .build()
      .parseClaimsJws(token);
  }

  private Key signingKey() {
    return Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
  }
}
