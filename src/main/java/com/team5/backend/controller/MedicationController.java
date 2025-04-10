package com.team5.backend.controller;

import com.team5.backend.entity.Medication;
import com.team5.backend.request.MarkMedicineAsTakenRequest;
import com.team5.backend.request.MedicationRequest;
import com.team5.backend.response.MedicationResponse;
import com.team5.backend.service.MedicationScheduleService;
import com.team5.backend.service.MedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medicine")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    private final MedicationScheduleService medicationScheduleService;

    @PostMapping
    public ResponseEntity<MedicationResponse> addMedicine(@Valid @RequestBody MedicationRequest request) {
        Medication medication = medicationService.addMedicine(request);
        MedicationResponse response = MedicationResponse.builder()
                .medication_id(medication.getMedication_id())
                .name(medication.getName())
                .quantity(medication.getQuantity())
                .capacity(medication.getCapacity())
                .message("Medicine added successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MedicationResponse>> getAllMedications() {
        return ResponseEntity.ok(medicationService.getAllMedications());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMedicine(@PathVariable Integer id) {
        medicationService.deleteMedication(id);
        return ResponseEntity.ok("{ \"message\": \"Medicine deleted successfully\" }");
    }

    @PatchMapping("/taken/{id}")
    public ResponseEntity<String> markMedicineAsTaken(@PathVariable Integer id,
            @RequestBody MarkMedicineAsTakenRequest request) {
        medicationScheduleService.markMedicineAsTaken(id, request);
        return ResponseEntity.ok("Medicine marked as taken successfully.");
    }
}
