package com.team5.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medication_schedule")
public class MedicationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer medication_schedule_id;

    @ManyToOne
    @JoinColumn(name = "medication_id", referencedColumnName = "medication_id")
    private Medication medication;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "user_id")
    private User patient;

    private LocalDateTime scheduled_time;

    private LocalDateTime time_taken;

    private Boolean taken;

    @Column(name = "notification_sent", nullable = false)
    private Boolean notification_sent;
}

