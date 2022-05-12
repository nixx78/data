package lv.nixx.poc.spring.jdbc.logevent;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Accessors(chain = true)
@Table("LOGENTRY_TBL")
public class LogEntry {
    @Id
    @Column("ID")
    private Long id;

    @Column("LEVEL")
    private String level;

    @Column("MESSAGE")
    private String message;

    @Column("TIMESTAMP")
    private LocalDateTime timestamp;

    @DomainEvents
    Collection<LogEntryEvent> domainEvents() {
        return List.of(new LogEntryEvent(this));
    }

    @AfterDomainEventPublication
    void callbackMethod() {
        System.out.println("Method with @AfterDomainEventPublication called");
    }

}
