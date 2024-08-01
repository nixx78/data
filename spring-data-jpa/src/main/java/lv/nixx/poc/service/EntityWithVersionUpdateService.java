package lv.nixx.poc.service;

import jakarta.transaction.Transactional;
import lv.nixx.poc.orm.EntityWithVersion;
import lv.nixx.poc.repository.EntityWithVersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class EntityWithVersionUpdateService {

    private static final Logger log = LoggerFactory.getLogger(EntityWithVersionUpdateService.class);

    private final EntityWithVersionRepository repository;

    private final TransactionTemplate template;

    public EntityWithVersionUpdateService(EntityWithVersionRepository repository, PlatformTransactionManager transactionManager) {
        this.repository = repository;
        this.template = new TransactionTemplate(transactionManager);
    }

    @Transactional
    public void updateEntityWithLock(Long id, String newName) {
        try {

            EntityWithVersion entityForUpdate = repository.findByIdWithLock(id);

            log.info("Entity for update {}", entityForUpdate);

            // !!! TimeUnit.SECONDS.sleep(2);

            // Если установить задержку, то будет ошибка, поскольку запись заблокирована другим потоком:
            // org.h2.jdbc.JdbcSQLTimeoutException: Timeout trying to lock table "ENTITY_WITH_VERSION"; SQL statement:
            // select ewv1_0.id,ewv1_0.data,ewv1_0.version from ENTITY_WITH_VERSION ewv1_0 where ewv1_0.id=? for update [50200-224]
            entityForUpdate.setData(newName);
            EntityWithVersion saved = repository.save(entityForUpdate);

            log.info("Saved entity: {}", saved);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
