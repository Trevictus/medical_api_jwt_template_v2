
package com.example.medical.security;

import com.example.medical.dto.auth.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JwtIntegrationTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper om;

  @Test
  void login_validCredentials_returnsToken() throws Exception {
    mvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(om.writeValueAsString(new LoginRequest("admin@example.com", "Admin1234!"))))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.token").exists())
      .andExpect(jsonPath("$.tokenType").value("Bearer"));
  }
}
