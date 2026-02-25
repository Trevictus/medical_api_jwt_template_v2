
package com.example.medical.web;

import com.example.medical.dto.patient.PatientCreateRequest;
import com.example.medical.dto.patient.PatientResponse;
import com.example.medical.error.NotFoundException;
import com.example.medical.repo.UserRepository;
import com.example.medical.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import com.example.medical.security.SecurityConfig;
import com.example.medical.security.JwtAuthFilter;
import com.example.medical.security.JwtService;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
@Import({SecurityConfig.class, JwtAuthFilter.class, JwtService.class})
class PatientControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    @MockBean
    PatientService service;

    @MockBean
    UserRepository userRepository;

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

    @Test
    @WithMockUser(roles = "ADMIN")
    void getPatients_notFound_returns404() throws Exception {
        //Se lanza excepción al no encontrar el paciente con id 91
        when(service.getById(91L)).thenThrow(new NotFoundException("Patient not found"));

        mvc.perform(get("/patients/91"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Patient not found"))
                .andExpect(jsonPath("$.path").value("/patients/91"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deletePatient_returns204() throws Exception {
        //Si el paciente existe no lanza excepción
        doNothing().when(service).delete(1L);

        mvc.perform(delete("/patients/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void putPatient_returns200AndUpdate() throws Exception {
        //Devuelve el paciente actualizado
        PatientResponse update = new PatientResponse(1L, "123", "Ana", "Lopez", "611111111", true);
        when(service.update(eq(1L), any(PatientCreateRequest.class))).thenReturn(update);

        mvc.perform(put("/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(new PatientCreateRequest("123", "Ana", "Lopez", "611111111"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ana"))
                .andExpect(jsonPath("$.lastName").value("Lopez"));
    }
}
