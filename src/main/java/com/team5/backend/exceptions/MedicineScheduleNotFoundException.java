package com.team5.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MedicineScheduleNotFoundException extends RuntimeException {
    public MedicineScheduleNotFoundException(String message) {
        super(message);
    }
}

