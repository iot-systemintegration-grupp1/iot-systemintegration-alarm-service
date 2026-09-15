package se.group1.alarmservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.group1.alarmservice.model.AlarmEntity;

import java.util.List;
import java.util.UUID;

public interface AlarmJpaRepository
        extends JpaRepository<AlarmEntity, UUID> {
    List<AlarmEntity> findByDeviceId(String deviceId);
}
