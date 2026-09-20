package se.group1.alarmservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.group1.alarmservice.model.ThresholdEntity;

import java.util.Optional;
import java.util.UUID;

public interface ThresholdJpaRepository
        extends JpaRepository<ThresholdEntity, UUID> {

    Optional<ThresholdEntity> findByMeasurementType(String measurementType);

    boolean existsByMeasurementType(String measurementType);
}