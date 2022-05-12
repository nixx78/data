package lv.nixx.poc.spring.jdbc.logevent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LogEntryEvent {
    private LogEntry logEntry;
}
