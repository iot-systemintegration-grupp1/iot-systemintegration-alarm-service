package se.group1.alarmservice.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.math.BigDecimal;

@ConfigurationProperties(prefix = "alarm")
public record AlarmThresholdProperties(
        Threshold temperature,
        Threshold humidity,
        Threshold pressure,
        Threshold vibration
) {
    public record Threshold (
            BigDecimal minimum,
            BigDecimal maximum
    ) {}
}
