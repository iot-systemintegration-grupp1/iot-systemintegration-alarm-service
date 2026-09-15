package se.group1.alarmservice.configurations;

import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.group1.alarmservice.services.AlarmServiceEndpoint;

@Configuration
public class WebServiceConfiguration {

    private final Bus bus;
    private final AlarmServiceEndpoint alarmServiceEndpoint;

    public WebServiceConfiguration(
            Bus bus,
            AlarmServiceEndpoint alarmServiceEndpoint) {

        this.bus = bus;
        this.alarmServiceEndpoint = alarmServiceEndpoint;
    }

    @Bean
    public Endpoint alarmEndpoint() {
        var endpoint = new EndpointImpl(bus, alarmServiceEndpoint);

        endpoint.setWsdlLocation("classpath:wsdl/alarm-service.wsdl");
        endpoint.publish("/alarms");

        return endpoint;
    }
}