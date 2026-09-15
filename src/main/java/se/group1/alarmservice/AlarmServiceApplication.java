package se.group1.alarmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AlarmServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlarmServiceApplication.class, args);
    }
}
