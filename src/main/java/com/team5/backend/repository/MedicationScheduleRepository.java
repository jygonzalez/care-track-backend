package com.team5.backend.repository;

import com.team5.backend.entity.Medication;
import com.team5.backend.entity.MedicationSchedule;
import com.team5.backend.entity.User;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationScheduleRepository extends JpaRepository<MedicationSchedule, Integer> {
    List<MedicationSchedule> findByPatient(User patient);

    void deleteByMedication(Medication medication);

    @Query("SELECT s FROM MedicationSchedule s WHERE s.scheduled_time BETWEEN :start AND :end AND s.taken = false")
    List<MedicationSchedule> findByScheduledTimeBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT s FROM MedicationSchedule s WHERE s.patient = :patient AND s.scheduled_time BETWEEN :start AND :end")
    List<MedicationSchedule> findByPatientAndScheduledTimeBetween(
            @Param("patient") User patient,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("SELECT s FROM MedicationSchedule s WHERE s.taken = true AND s.time_taken IS NOT NULL AND s.notification_sent = false")
    List<MedicationSchedule> findNotNotifiedTakenDoses();

    @Query("SELECT s FROM MedicationSchedule s WHERE s.taken = false AND s.scheduled_time < :now AND s.notification_sent = false")
    List<MedicationSchedule> findNotNotifiedMissedDoses(@Param("now") LocalDateTime now);

    @Query("SELECT s FROM MedicationSchedule s WHERE s.taken = false AND s.scheduled_time < :now")
    List<MedicationSchedule> findMissedDoses(@Param("now") LocalDateTime now);

    @Query("SELECT s FROM MedicationSchedule s WHERE s.patient = :patient AND s.scheduled_time <= :endOfDay")
    List<MedicationSchedule> findDueMedications(@Param("patient") User patient, @Param("endOfDay") LocalDateTime endOfDay);
}
