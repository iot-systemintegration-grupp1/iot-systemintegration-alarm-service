package se.group1.alarmservice.services;

import org.springframework.stereotype.Component;
import system.group1.alarm_service.EvaluateMeasurementRequest;

@Component
public class MeasurementValidator {

    public void validate(EvaluateMeasurementRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Measurement request is required"
            );
        }

        if (request.getMeasurementId() == null
                || request.getMeasurementId().isBlank()) {
            throw new IllegalArgumentException(
                    "MeasurementId is required"
            );
        }

        if (request.getDeviceId() == null
                || request.getDeviceId().isBlank()) {
            throw new IllegalArgumentException(
                    "DeviceId is required"
            );
        }

        if (request.getMeasuredAt() == null) {
            throw new IllegalArgumentException(
                    "MeasuredAt is required"
            );
        }

        if (request.getValue() == null) {
            throw new IllegalArgumentException(
                    "Value is required"
            );
        }

        if (request.getMeasurementType() == null
                || request.getMeasurementType().isBlank()) {
            throw new IllegalArgumentException(
                    "MeasurementType is required"
            );
        }

        if (request.getUnit() == null
                || request.getUnit().isBlank()) {
            throw new IllegalArgumentException(
                    "Unit is required"
            );
        }
    }
}