package se.group1.alarmservice.services;

import jakarta.jws.WebService;
import org.springframework.stereotype.Service;
import system.group1.alarm_service.*;

@Service
@WebService(
        serviceName = "AlarmService",
        portName = "AlarmServicePort",
        targetNamespace = "https://group1.system/alarm-service",
        endpointInterface = "system.group1.alarm_service.AlarmServicePortType"
)
public class AlarmServiceEndpoint implements AlarmServicePortType {

    private final SoapAlarmService soapAlarmService;

    public AlarmServiceEndpoint(
            SoapAlarmService soapAlarmService) {

        this.soapAlarmService = soapAlarmService;
    }

    @Override
    public EvaluateMeasurementResponse evaluateMeasurement(
            EvaluateMeasurementRequest parameters)
            throws InternalServiceFaultMessage {

        return soapAlarmService.evaluateMeasurement(parameters);
    }

    @Override
    public GetAlarmsByDeviceIdResponse getAlarmsByDeviceId(
            GetAlarmsByDeviceIdRequest parameters)
            throws InternalServiceFaultMessage {

        return soapAlarmService.getAlarmsByDeviceId(parameters);
    }

    @Override
    public GetAllAlarmsResponse getAllAlarms(
            GetAllAlarmsRequest parameters)
            throws InternalServiceFaultMessage {

        return soapAlarmService.getAllAlarms();
    }
}