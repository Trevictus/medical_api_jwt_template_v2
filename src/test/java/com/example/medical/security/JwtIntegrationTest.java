
package com.example.medical.security;

import com.example.medical.dto.auth.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JwtIntegrationTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper om;

  @Test
  //Credenciales válidas /auth/login
  void login_validCredentials_returnsToken() throws Exception {
    mvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(om.writeValueAsString(new LoginRequest("admin@example.com", "Admin1234!"))))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.token").exists())
      .andExpect(jsonPath("$.tokenType").value("Bearer"));
  }

  @Test
  //Acceso sin token a endpoint protegido
  void accessProtectedEndpoint_withoutToken_returns401() throws Exception{

    mvc.perform(get("/patients"))
            .andExpect(status().isUnauthorized());
  }

  //Función para hacer login obteniendo token de JWT
  private String obtenerToken(String email, String password) throws Exception{
    MvcResult result = mvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(new LoginRequest(email, password))))
            .andExpect(status().isOk())
            .andReturn();

    String responseBody = result.getResponse().getContentAsString();
    return om.readTree(responseBody).get("token").asText();
  }

  @Test
  //Acceso con rol no permitido
  void accessPatient_withPacientRole_return403() throws Exception{

    String tokenPaciente = obtenerToken("pat@example.com", "Pat1234!");

    mvc.perform(get("/patients")
            .header("Authorization", "Bearer " + tokenPaciente))
            .andExpect(status().isForbidden());
  }

  @Test
  //Acceso con rol permitido
  void accesPatient_withAdminRole_return200() throws Exception{

    String tokenAdmin = obtenerToken("admin@example.com", "Admin1234!");

    mvc.perform(get("/patients")
            .header("Authorization", "Bearer " + tokenAdmin))
            .andExpect(status().isOk());
  }
}
