package com.team5.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team5.backend.entity.Medication;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {
}