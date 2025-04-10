package com.team5.backend.service;

import com.team5.backend.entity.Medication;
import com.team5.backend.entity.MedicationSchedule;
import com.team5.backend.entity.User;
import com.team5.backend.repository.MedicationRepository;
import com.team5.backend.repository.MedicationScheduleRepository;
import com.team5.backend.request.MedicationRequest;
import com.team5.backend.response.MedicationResponse;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final MedicationScheduleRepository medicationScheduleRepository;

    public Medication addMedicine(MedicationRequest request) {
        User patient = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        // Create  medication record
        Medication medication = Medication.builder()
                .name(request.getName())
                .quantity(request.getQuantity())
                .capacity(request.getCapacity())
                .build();

        medication = medicationRepository.save(medication);

        // Create medication schedule record
        MedicationSchedule schedule = MedicationSchedule.builder()
                .medication(medication)
                .patient(patient)
                .scheduled_time(request.getScheduled_time())
                .taken(false)
                .notification_sent(false)
                .build();

        medicationScheduleRepository.save(schedule);

        return medication;
    }

    public List<MedicationResponse> getAllMedications() {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59); // End of the day

        // Fetch medications that are scheduled for the authenticated user and are due today or past due
        List<MedicationSchedule> schedules = medicationScheduleRepository.findDueMedications(authUser, endOfDay);

        return schedules.stream().map(schedule ->
                MedicationResponse.builder()
                        .medication_id(schedule.getMedication().getMedication_id())
                        .name(schedule.getMedication().getName())
                        .quantity(schedule.getMedication().getQuantity())
                        .capacity(schedule.getMedication().getCapacity())
                        .scheduled_time(schedule.getScheduled_time())
                        .message("Successfully retrieved scheduled medicines")
                        .build()
        ).collect(Collectors.toList());
    }
    
    @Transactional
    public void deleteMedication(Integer id) {
        Medication medication = medicationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medicine not found"));

        medicationScheduleRepository.deleteByMedication(medication);
        
        medicationRepository.delete(medication);
    }
}
