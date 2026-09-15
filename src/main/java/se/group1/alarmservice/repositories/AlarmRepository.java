package se.group1.alarmservice.repositories;

import system.group1.alarm_service.AlarmResult;
import java.util.List;

public interface AlarmRepository {

    void save(AlarmResult alarm);

    List<AlarmResult> getAll();

    List<AlarmResult> getByDeviceId(String deviceId);
}