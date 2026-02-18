
package com.example.medical;

import com.example.medical.domain.*;
import com.example.medical.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MedicalApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(MedicalApiApplication.class, args);
  }

  @Bean
  CommandLineRunner seedUsers(UserRepository users, PasswordEncoder encoder) {
    return args -> {
      if (users.count() > 0) return;

      users.save(AppUser.create("admin@example.com", encoder.encode("Admin1234!"), Role.ADMIN));
      users.save(AppUser.create("recep@example.com", encoder.encode("Recep1234!"), Role.RECEPCIONISTA));
      users.save(AppUser.create("doc@example.com", encoder.encode("Doc1234!"), Role.MEDICO));
      users.save(AppUser.create("pat@example.com", encoder.encode("Pat1234!"), Role.PACIENTE));
    };
  }
}
