package lv.nixx.poc.poc.locking;

import lv.nixx.poc.orm.EntityWithVersion;
import lv.nixx.poc.repository.EntityWithVersionRepository;
import lv.nixx.poc.service.EntityWithVersionUpdateService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class PessimisticLockSample {

    private static final Logger log = LoggerFactory.getLogger(PessimisticLockSample.class);

    @Autowired
    EntityWithVersionRepository repository;

    @Autowired
    EntityWithVersionUpdateService service;

    @Autowired
    PlatformTransactionManager transactionManager;

    Long idForUpdate;

    @BeforeAll
    void init() {

        TransactionTemplate templ = new TransactionTemplate(transactionManager);

        templ.executeWithoutResult((s -> {
            EntityWithVersion entity = new EntityWithVersion("Initial Name");
            EntityWithVersion save = repository.save(entity);
            idForUpdate = save.getId();
        }));
    }

    @Test
    @Transactional
    void pessimisticLockSample() {

        Runnable task1 = () -> {
            try {
                service.updateEntityWithLock(idForUpdate, "Updated Name-1");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        };

        Runnable task2 = () -> {
            try {
                service.updateEntityWithLock(idForUpdate, "Updated Name-2");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        };


        Thread thread1 = new Thread(task1, "UpdateThread-1");
        Thread thread2 = new Thread(task2, "UpdateThread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        List<EntityWithVersion> all = repository.findAll();
        log.info("Data in table after updates: ");
        all.forEach(t -> log.info(t.toString()));
    }

}
