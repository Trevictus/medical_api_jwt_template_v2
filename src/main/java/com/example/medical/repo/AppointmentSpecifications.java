package com.example.medical.repo;

import com.example.medical.domain.Appointment;
import com.example.medical.domain.AppointmentStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase con métodos estáticos que crean Specifications para filtrar Appointments.
 * Cada método devuelve un Specification que representa una condición WHERE.
 */
public class AppointmentSpecifications {

    /**
     * Filtra por ID de doctor.
     * Equivale a: WHERE doctor_id = :doctorId
     */
    public static Specification<Appointment> byDoctorId(Long doctorId) {
        return (root, query, cb) ->
                doctorId == null ? null : cb.equal(root.get("doctor").get("id"), doctorId);
    }

    /**
     * Filtra por ID de paciente.
     * Equivale a: WHERE patient_id = :patientId
     */
    public static Specification<Appointment> byPatientId(Long patientId) {
        return (root, query, cb) ->
                patientId == null ? null : cb.equal(root.get("patient").get("id"), patientId);
    }

    /**
     * Filtra por estado de la cita.
     * Equivale a: WHERE status = :status
     */
    public static Specification<Appointment> byStatus(AppointmentStatus status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    /**
     * Filtra citas desde una fecha.
     * Equivale a: WHERE start_at >= :dateFrom (a las 00:00)
     */
    public static Specification<Appointment> byDateFrom(LocalDate dateFrom) {
        return (root, query, cb) -> {
            if (dateFrom == null) return null;
            LocalDateTime startOfDay = dateFrom.atStartOfDay();
            return cb.greaterThanOrEqualTo(root.get("startAt"), startOfDay);
        };
    }

    /**
     * Filtra citas hasta una fecha.
     * Equivale a: WHERE start_at < :dateTo+1día (a las 00:00)
     */
    public static Specification<Appointment> byDateTo(LocalDate dateTo) {
        return (root, query, cb) -> {
            if (dateTo == null) return null;
            LocalDateTime endOfDay = dateTo.plusDays(1).atStartOfDay();
            return cb.lessThan(root.get("startAt"), endOfDay);
        };
    }
}