package se.group1.alarmservice.repositories;

import org.springframework.stereotype.Repository;
import se.group1.alarmservice.mapper.AlarmMapper;
import se.group1.alarmservice.model.AlarmEntity;
import system.group1.alarm_service.AlarmResult;

import java.util.List;

@Repository
public class AlarmMessageRepository implements AlarmRepository {

    private final AlarmJpaRepository jpaRepository;
    private final AlarmMapper alarmMapper;

    public AlarmMessageRepository(
            AlarmJpaRepository jpaRepository,
            AlarmMapper alarmMapper) {

        this.jpaRepository = jpaRepository;
        this.alarmMapper = alarmMapper;
    }

    @Override
    public void save(AlarmResult alarm) {
        AlarmEntity entity = alarmMapper.toEntity(alarm);

        jpaRepository.save(entity);
    }

    @Override
    public List<AlarmResult> getAll() {

        return jpaRepository.findAll()
                .stream()
                .map(alarmMapper::toAlarmResult)
                .toList();
    }

    @Override
    public List<AlarmResult> getByDeviceId(String deviceId) {
        return jpaRepository.findByDeviceId(deviceId)
                .stream()
                .map(alarmMapper::toAlarmResult)
                .toList();
    }
}