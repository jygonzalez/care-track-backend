package com.team5.backend.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicationResponse {
    private Integer medication_id;
    private String name;
    private Integer quantity;
    private Integer capacity;
    private LocalDateTime scheduled_time;
    private String message;
}
