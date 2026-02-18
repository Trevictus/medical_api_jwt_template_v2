
package com.example.medical.web;

import com.example.medical.dto.patient.PatientCreateRequest;
import com.example.medical.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import com.example.medical.security.SecurityConfig;
import com.example.medical.security.JwtAuthFilter;
import com.example.medical.security.JwtService;
import com.example.medical.security.JwtProperties;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
@Import({SecurityConfig.class, JwtAuthFilter.class, JwtService.class, JwtProperties.class})
class PatientControllerTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper om;

  @MockBean PatientService service;

  @Test
  void postPatients_returns201AndLocation_template() throws Exception {
    // TODO: build a valid JWT or switch to @SpringBootTest for easier JWT tests
    // This test is provided as a placeholder; students should implement the full version in integration tests.
    Mockito.when(service.create(Mockito.any())).thenAnswer(inv -> {
      var req = (PatientCreateRequest) inv.getArgument(0);
      return new com.example.medical.dto.patient.PatientResponse(1L, req.dni(), req.firstName(), req.lastName(), req.phone(), true);
    });

    // With Security enabled, unauthenticated request must be 401
    mvc.perform(post("/patients")
        .contentType(MediaType.APPLICATION_JSON)
        .content(om.writeValueAsString(new PatientCreateRequest("123", "Ana", "Lopez", "600000000"))))
      .andExpect(status().isUnauthorized());
  }
}
