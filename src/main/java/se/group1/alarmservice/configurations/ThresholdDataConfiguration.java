package se.group1.alarmservice.configurations;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.group1.alarmservice.model.ThresholdEntity;
import se.group1.alarmservice.repositories.ThresholdJpaRepository;

@Configuration
public class ThresholdDataConfiguration {

    @Bean
    ApplicationRunner seedThresholds(
            ThresholdJpaRepository thresholdRepository,
            AlarmThresholdProperties thresholds) {

        return arguments -> {
            seedIfMissing(
                    thresholdRepository,
                    "temperature",
                    thresholds.temperature()
            );
            seedIfMissing(
                    thresholdRepository,
                    "humidity",
                    thresholds.humidity()
            );
            seedIfMissing(
                    thresholdRepository,
                    "pressure",
                    thresholds.pressure()
            );
            seedIfMissing(
                    thresholdRepository,
                    "vibration",
                    thresholds.vibration()
            );
        };
    }

    private void seedIfMissing(
            ThresholdJpaRepository thresholdRepository,
            String measurementType,
            AlarmThresholdProperties.Threshold threshold) {

        if (thresholdRepository.existsByMeasurementType(measurementType)) {
            return;
        }

        var entity = new ThresholdEntity();
        entity.setMeasurementType(measurementType);
        entity.setMinimumValue(threshold.minimum());
        entity.setMaximumValue(threshold.maximum());

        thresholdRepository.save(entity);
    }
}
