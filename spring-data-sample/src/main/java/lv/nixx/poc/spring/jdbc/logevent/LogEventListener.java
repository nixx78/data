package lv.nixx.poc.spring.jdbc.logevent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LogEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(LogEventListener.class);

    @EventListener
    public void onMyDomainEvent(LogEntryEvent event) {
        LOG.info("Event in LogEventListener: {}", event);
    }
}
