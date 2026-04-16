package com.java.backend.dto;

import com.java.backend.model.Patient;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PredictionDTO {
    private LocalDateTime DateAndTime;
    private String riskScore;
    private String result; // temp
    private String belongsTo;
}
