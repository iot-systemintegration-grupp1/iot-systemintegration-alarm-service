package se.group1.alarmservice.mapper;

import org.springframework.stereotype.Component;
import se.group1.alarmservice.model.AlarmEntity;
import system.group1.alarm_service.AlarmResult;

import javax.xml.datatype.DatatypeFactory;
import java.util.GregorianCalendar;

@Component
public class AlarmMapper {

    public AlarmEntity toEntity(AlarmResult alarm) {

        return new AlarmEntity(
                alarm.getDeviceId(),
                alarm.getMeasurementId(),
                alarm.getCreatedAt()
                        .toGregorianCalendar()
                        .toZonedDateTime()
                        .toOffsetDateTime(),
                alarm.getMeasuredAt()
                        .toGregorianCalendar()
                        .toZonedDateTime()
                        .toOffsetDateTime(),
                alarm.getValue(),
                alarm.getMeasurementType(),
                alarm.getUnit(),
                alarm.getMessage()
        );
    }

    public AlarmResult toAlarmResult(AlarmEntity entity) {

        AlarmResult alarm = new AlarmResult();

        alarm.setAlarmCreated("Alarm created");
        alarm.setDeviceId(entity.getDeviceId());
        alarm.setMeasurementId(entity.getMeasurementId());
        alarm.setValue(entity.getvalue());
        alarm.setMeasurementType(entity.getGetMeasurementType());
        alarm.setUnit(entity.getUnit());
        alarm.setMessage(entity.getMessage());

        try {
            GregorianCalendar createdAt =
                    GregorianCalendar.from(
                            entity.getCreatedAt().toZonedDateTime()
                    );

            alarm.setCreatedAt(
                    DatatypeFactory
                            .newInstance()
                            .newXMLGregorianCalendar(createdAt)
            );

            GregorianCalendar measuredAt =
                    GregorianCalendar.from(
                            entity.getMeasuredAt().toZonedDateTime()
                    );

            alarm.setMeasuredAt(
                    DatatypeFactory
                            .newInstance()
                            .newXMLGregorianCalendar(measuredAt)
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Could not map alarm timestamps",
                    e
            );
        }

        return alarm;
    }
}
