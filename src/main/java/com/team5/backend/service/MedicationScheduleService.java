package com.team5.backend.service;

import com.team5.backend.entity.MedicationSchedule;
import com.team5.backend.exceptions.MedicineScheduleNotFoundException;
import com.team5.backend.repository.MedicationScheduleRepository;
import com.team5.backend.request.MarkMedicineAsTakenRequest;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicationScheduleService {

    private final MedicationScheduleRepository medicationScheduleRepository;

    public MedicationSchedule markMedicineAsTaken(Integer scheduleId, MarkMedicineAsTakenRequest request) {
        MedicationSchedule medicationSchedule = medicationScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new MedicineScheduleNotFoundException("Medicine schedule not found"));
        
        medicationSchedule.setTaken(request.isTaken());
        
        if (request.isTaken()) {
            medicationSchedule.setTime_taken(LocalDateTime.now());
        }
        
        return medicationScheduleRepository.save(medicationSchedule);
    }

}

