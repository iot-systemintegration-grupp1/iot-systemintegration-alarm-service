package se.group1.alarmservice.services;

import org.springframework.stereotype.Service;
import se.group1.alarmservice.repositories.AlarmRepository;
import system.group1.alarm_service.*;
import javax.xml.datatype.DatatypeFactory;
import java.util.GregorianCalendar;

@Service
public class SoapAlarmService {

    private final AlarmRepository repository;
    private final MeasurementEvaluator evaluator;
    private final MeasurementValidator validator;

    public SoapAlarmService(
            AlarmRepository repository,
            MeasurementEvaluator evaluator,
            MeasurementValidator validator) {

        this.repository = repository;
        this.evaluator = evaluator;
        this.validator = validator;
    }

    public EvaluateMeasurementResponse evaluateMeasurement(
            EvaluateMeasurementRequest request)
            throws InternalServiceFaultMessage {

        try {

            validator.validate(request);

            var response = new EvaluateMeasurementResponse();

            boolean alarmCreated =
                    evaluator.shouldCreateAlarm(request);

            response.setAlarmCreated(alarmCreated);

            if (!alarmCreated) {
                return response;
            }

            var alarm = createAlarm(request);

            repository.save(alarm);

            response.setAlarm(alarm);

            return response;

        } catch (IllegalArgumentException e) {

            var fault = new InternalServiceFault();

            fault.setMessage(e.getMessage());

            throw new InternalServiceFaultMessage(
                    e.getMessage(),
                    fault,
                    e
            );
        }
    }

    public GetAlarmsByDeviceIdResponse getAlarmsByDeviceId(
            GetAlarmsByDeviceIdRequest request)
            throws InternalServiceFaultMessage {

        var response = new GetAlarmsByDeviceIdResponse();

        var alarms =
                repository.getByDeviceId(request.getDeviceId());

        response.getAlarm().addAll(alarms);

        return response;
    }

    public GetAllAlarmsResponse getAllAlarms()
            throws InternalServiceFaultMessage {

        var response = new GetAllAlarmsResponse();

        response.getAlarm().addAll(repository.getAll());

        return response;
    }

    private AlarmResult createAlarm(
            EvaluateMeasurementRequest request) {

        var alarm = new AlarmResult();

        alarm.setAlarmCreated("Alarm created");
        alarm.setDeviceId(request.getDeviceId());
        alarm.setMeasurementId(request.getMeasurementId());
        alarm.setMeasuredAt(request.getMeasuredAt());
        alarm.setValue(request.getValue());
        alarm.setMeasurementType(request.getMeasurementType());
        alarm.setUnit(request.getUnit());

        alarm.setMessage(
                request.getMeasurementType()
                        + " threshold exceeded"
        );

        try {
            var now = new GregorianCalendar();

            alarm.setCreatedAt(
                    DatatypeFactory
                            .newInstance()
                            .newXMLGregorianCalendar(now)
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Could not create alarm timestamp",
                    e
            );
        }

        return alarm;
    }
}