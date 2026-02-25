
package com.example.medical.repo;

import com.example.medical.domain.Appointment;
import com.example.medical.domain.AppointmentStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {

  List<Appointment> findByDoctorId(Long doctorId);

  List<Appointment> findByDoctorIdAndStatus(Long doctorId, AppointmentStatus status);

  List<Appointment> findByDoctorIdAndStartAtBetween(Long doctorId, LocalDateTime start, LocalDateTime end);
}
