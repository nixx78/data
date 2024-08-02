package lv.nixx.poc.poc.locking;

import lv.nixx.poc.orm.EntityWithVersion;
import lv.nixx.poc.repository.EntityWithVersionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OptimisticLockSample {

    private static final Logger log = LoggerFactory.getLogger(OptimisticLockSample.class);

    @Autowired
    EntityWithVersionRepository repository;

    @Test
    void optimisticLockSample() {

        EntityWithVersion initialValue = repository.save(new EntityWithVersion("Initial value"));
        Long idToUpdate = repository.save(initialValue).getId();

        Thread thread1 = new UpdateTask(idToUpdate, "Updated Value.1", "UpdateThread-1");
        Thread thread2 = new UpdateTask(idToUpdate, "Updated Value.2", "UpdateThread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }


        List<EntityWithVersion> all = repository.findAll();
        log.info("Data in table after update");
        all.forEach(t -> log.info(t.toString()));
    }


    class UpdateTask extends Thread {

        final Long id;
        final String newValue;

        UpdateTask(Long id, String newValue, String name) {
            super(name);
            this.id = id;
            this.newValue = newValue;
        }

        @Override
        public void run() {
            try {
                // получаем данные из таблицы
                EntityWithVersion entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
                entity.setData(newValue);

                log.info("Try to update entity: {}", entity);

                // перед сохранением, опять запрашиваем данные, чтобы проверить, что версия не изменилась
                EntityWithVersion saved = repository.save(entity);
                log.info("Entity updated to: {}", saved);
            } catch (ObjectOptimisticLockingFailureException e) {
                log.error("Optimistic lock exception: " + e.getMessage());
            }
        }
    }


}
