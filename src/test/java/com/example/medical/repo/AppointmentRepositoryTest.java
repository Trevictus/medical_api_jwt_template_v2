
package com.example.medical.repo;

import com.example.medical.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppointmentRepositoryTest {

  @Autowired AppointmentRepository appts;

  @Test
  void repositoryLoads_template() {
    assertNotNull(appts);
  }
}
