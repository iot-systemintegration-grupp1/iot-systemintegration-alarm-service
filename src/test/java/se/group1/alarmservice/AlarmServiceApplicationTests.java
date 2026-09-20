package se.group1.alarmservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:alarm-service;MODE=MSSQLServer;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class AlarmServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
