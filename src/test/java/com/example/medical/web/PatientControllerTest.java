
package com.example.medical.web;

import com.example.medical.dto.patient.PatientCreateRequest;
import com.example.medical.dto.patient.PatientResponse;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
@Import({SecurityConfig.class, JwtAuthFilter.class, JwtService.class, JwtProperties.class})
class PatientControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    @MockBean
    PatientService service;

    @Test
    @WithMockUser(roles = "ADMIN")
    void postPatients_returns201AndLocation_template() throws Exception {
        PatientResponse response = new PatientResponse(1L, "123", "Ana", "Lopez", "600000000", true);
        when(service.create(any(PatientCreateRequest.class))).thenReturn(response);

        mvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(new PatientCreateRequest("123", "Ana", "Lopez", "600000000"))))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/patients/1")))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Ana"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void postPatients_invalidDto_return400() throws Exception {
        //Proporcionado Dto que no cumple con @NotBlank en PatientCreateRequest
        String invalidJson =
                """
                        {
                        "dni": "123",
                        "firstName": "",
                        "lastName: "Lopez",
                        "phone": "600000000"
                        }
                """;
        mvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());
    }
}
