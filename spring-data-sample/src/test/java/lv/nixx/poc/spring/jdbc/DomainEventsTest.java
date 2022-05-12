package lv.nixx.poc.spring.jdbc;

import lv.nixx.poc.spring.jdbc.logevent.LogEntry;
import lv.nixx.poc.spring.jdbc.repository.LogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class DomainEventsTest {

    @Autowired
    private LogRepository repository;

    @Test
    void publishEventTest() {

        LogEntry logMessage = repository.save(new LogEntry()
                .setLevel("INFO")
                .setMessage("Log message")
                .setTimestamp(LocalDateTime.now())
        );

        logMessage.setMessage("Updated message");

        repository.save(logMessage);
    }

}
