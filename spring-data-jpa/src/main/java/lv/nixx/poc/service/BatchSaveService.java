package lv.nixx.poc.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lv.nixx.poc.orm.EntityForBatchSave;
import lv.nixx.poc.repository.BatchDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class BatchSaveService {


    private final BatchDataRepository batchDataRepository;
    private final EntityManager entityManager;

    public BatchSaveService(BatchDataRepository batchDataRepository, EntityManager entityManager) {
        this.batchDataRepository = batchDataRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public void saveDataUsingBatch(int batchSize) {
//
        List<EntityForBatchSave> dataToSave = IntStream.range(0, batchSize)
                .mapToObj(t -> new EntityForBatchSave(null, "value." + t))
                .toList();
//
//        batchDataRepository.saveAll(dataToSave);

        for(EntityForBatchSave e: dataToSave) {
            entityManager.persist(e);
        }
    }


}
