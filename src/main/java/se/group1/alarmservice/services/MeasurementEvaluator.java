package se.group1.alarmservice.services;

import org.springframework.stereotype.Service;
import se.group1.alarmservice.model.ThresholdEntity;
import se.group1.alarmservice.repositories.ThresholdJpaRepository;
import system.group1.alarm_service.EvaluateMeasurementRequest;

import java.math.BigDecimal;

@Service
public class MeasurementEvaluator {

    private final ThresholdJpaRepository thresholdRepository;

    public MeasurementEvaluator(
            ThresholdJpaRepository thresholdRepository) {
        this.thresholdRepository = thresholdRepository;
    }

    public boolean shouldCreateAlarm(EvaluateMeasurementRequest request) {

        var threshold = thresholdRepository
                .findByMeasurementType(
                        request.getMeasurementType().toLowerCase()
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unknown measurement type: "
                                        + request.getMeasurementType()
                        )
                );

        BigDecimal value = request.getValue();

        if (threshold.getMaximumValue() != null
                && value.compareTo(threshold.getMaximumValue()) > 0) {
            return true;
        }

        if (threshold.getMinimumValue() != null
                && value.compareTo(threshold.getMinimumValue()) < 0) {
            return true;
        }

        return false;
    }

    public BigDecimal getMaximumThreshold(String measurementType) {

        return thresholdRepository
                .findByMeasurementType(measurementType.toLowerCase())
                .map(ThresholdEntity::getMaximumValue)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unknown measurement type: "
                                        + measurementType
                        )
                );
    }
}
