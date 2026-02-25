
package com.example.medical.repo;

import com.example.medical.config.TestConfig;
import com.example.medical.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestConfig.class)
class AppointmentRepositoryTest {

  @Autowired AppointmentRepository  appts;

  @Autowired
  TestEntityManager em;

  private Doctor doctor1;
  private Doctor doctor2;
  private Patient patient1;

  @BeforeEach
  void setUp(){
      AppUser user1 = new AppUser();
      user1.setEmail("doc1@test.com");
      user1.setPasswordHash("hash");
      user1.setRole(Role.MEDICO);
      em.persist(user1);

      AppUser user2 = new AppUser();
      user2.setEmail("doc2@test.com");
      user2.setPasswordHash("hash");
      user2.setRole(Role.MEDICO);
      em.persist(user2);

      doctor1 = new Doctor();
      doctor1.setUser(user1);
      doctor1.setLicenseNumber("COL001");
      doctor1.setFirstName("Doctor");
      doctor1.setLastName("Uno");
      em.persist(doctor1);

      doctor2 = new Doctor();
      doctor2.setUser(user2);
      doctor2.setLicenseNumber("COL002");
      doctor2.setFirstName("Doctor");
      doctor2.setLastName("Dos");
      em.persist(doctor2);

      patient1 = new Patient();
      patient1.setDni("11111111A");
      patient1.setFirstName("Paciente");
      patient1.setLastName("Uno");
      em.persist(patient1);

      em.flush();
  }

  private Appointment createAppointment(Doctor doctor, Patient patient, LocalDateTime start) {
    Appointment a = new Appointment();
    a.setDoctor(doctor);
    a.setPatient(patient);
    a.setStartAt(start);
    a.setEndAt(start.plusHours(1));
    a.setReason("consulta");
    return a;
  }

  @Test
    void findByDoctorId_returnsAppointment() {
    Appointment a1 = createAppointment(doctor1, patient1, LocalDateTime.now().plusDays(1));
    Appointment a2 = createAppointment(doctor2, patient1, LocalDateTime.now().plusDays(2));
    Appointment a3 = createAppointment(doctor1, patient1, LocalDateTime.now().plusDays(3));
    em.persist(a1);
    em.persist(a2);
    em.persist(a3);
    em.flush();

    List<Appointment> results = appts.findByDoctorId(doctor1.getId());
    assertEquals(2, results.size());
    assertTrue(results.stream().allMatch(a -> a.getDoctor().getId().equals(doctor1.getId())));
    }

    @Test
    void findByDoctorIdAndStartAtBetween_returnsCorrectRange() {
        LocalDateTime now = LocalDateTime.now();
        Appointment a1 = createAppointment(doctor1, patient1, now.plusDays(1));  // Dentro del rango
        Appointment a2 = createAppointment(doctor1, patient1, now.plusDays(5));  // Dentro del rango
        Appointment a3 = createAppointment(doctor1, patient1, now.plusDays(15)); // Fuera del rango

        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.flush();

        List<Appointment> result = appts.findByDoctorIdAndStartAtBetween(
                doctor1.getId(),
                now,
                now.plusDays(10)
        );

        assertEquals(2, result.size());
    }

    @Test
    void findByDoctorIdAndStatus_returnsCorrectAppointments() {
      Appointment a1 = createAppointment(doctor1, patient1, LocalDateTime.now().plusDays(1));
      a1.setStatus(AppointmentStatus.PROGRAMADA);
      Appointment a2 = createAppointment(doctor1, patient1, LocalDateTime.now().plusDays(2));
      a2.setStatus(AppointmentStatus.CANCELADA);
      Appointment a3 = createAppointment(doctor1, patient1, LocalDateTime.now().plusDays(3));
      a3.setStatus(AppointmentStatus.PROGRAMADA);

      em.persist(a1);
      em.persist(a2);
      em.persist(a3);
      em.flush();

      List<Appointment> result = appts.findByDoctorIdAndStatus(doctor1.getId(), AppointmentStatus.PROGRAMADA);

      assertEquals(2, result.size());
      assertTrue(result.stream().allMatch(a -> a.getStatus().equals(AppointmentStatus.PROGRAMADA)));
    }

    @Test
    void saveDuplicateDni_throwsException() {
        Patient p2 = new Patient();
        p2.setDni("11111111A");
        p2.setFirstName("Paciente");
        p2.setLastName("Dos");

        assertThrows(Exception.class, () -> {
            em.persist(p2);
            em.flush();
        });
    }
}
