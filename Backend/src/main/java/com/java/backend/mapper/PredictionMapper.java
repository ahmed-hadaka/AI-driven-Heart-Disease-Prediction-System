package com.java.backend.mapper;

import com.java.backend.dto.PredictionDTO;
import com.java.backend.model.Prediction;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class  PredictionMapper {

    public PredictionDTO toDTO(Prediction prediction){
        PredictionDTO predictionDTO = new PredictionDTO();
        predictionDTO.setDateAndTime(prediction.getCreatedAt());
        predictionDTO.setResult(prediction.getPredictionResult());
        predictionDTO.setRiskScore(prediction.getRiskScore());
        predictionDTO.setBelongsTo(prediction.getPatient().getEmail());
        return predictionDTO;
    }
}
