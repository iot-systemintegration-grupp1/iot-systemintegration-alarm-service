package se.group1.alarmservice.configurations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.group1.alarmservice.model.ThresholdEntity;
import se.group1.alarmservice.repositories.ThresholdJpaRepository;

@ExtendWith(MockitoExtension.class)
class ThresholdDataConfigurationTests {

    @Mock
    private ThresholdJpaRepository thresholdRepository;

    @Test
    void seedsConfiguredThresholdsThatDoNotExist() throws Exception {
        when(thresholdRepository.existsByMeasurementType(any())).thenReturn(false);

        var configuration = new ThresholdDataConfiguration();
        var properties = new AlarmThresholdProperties(
                new AlarmThresholdProperties.Threshold(
                        BigDecimal.ZERO,
                        BigDecimal.valueOf(30)
                ),
                new AlarmThresholdProperties.Threshold(
                        BigDecimal.valueOf(20),
                        BigDecimal.valueOf(80)
                ),
                new AlarmThresholdProperties.Threshold(
                        BigDecimal.valueOf(950),
                        BigDecimal.valueOf(1050)
                ),
                new AlarmThresholdProperties.Threshold(
                        BigDecimal.ZERO,
                        BigDecimal.TEN
                )
        );

        configuration.seedThresholds(thresholdRepository, properties).run(null);

        var thresholdCaptor = ArgumentCaptor.forClass(ThresholdEntity.class);
        verify(thresholdRepository, org.mockito.Mockito.times(4))
                .save(thresholdCaptor.capture());

        Assertions.assertEquals(
                List.of("temperature", "humidity", "pressure", "vibration"),
                thresholdCaptor.getAllValues().stream()
                        .map(ThresholdEntity::getMeasurementType)
                        .toList()
        );
    }

    @Test
    void keepsExistingThresholds() throws Exception {
        when(thresholdRepository.existsByMeasurementType(any())).thenReturn(true);

        var configuration = new ThresholdDataConfiguration();
        var properties = new AlarmThresholdProperties(
                new AlarmThresholdProperties.Threshold(BigDecimal.ZERO, BigDecimal.TEN),
                new AlarmThresholdProperties.Threshold(BigDecimal.ZERO, BigDecimal.TEN),
                new AlarmThresholdProperties.Threshold(BigDecimal.ZERO, BigDecimal.TEN),
                new AlarmThresholdProperties.Threshold(BigDecimal.ZERO, BigDecimal.TEN)
        );

        configuration.seedThresholds(thresholdRepository, properties).run(null);

        verify(thresholdRepository, never()).save(any(ThresholdEntity.class));
    }
}
