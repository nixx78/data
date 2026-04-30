package lv.nixx.poc.rest;

import lv.nixx.poc.service.BatchSaveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchInsertController {

    private final BatchSaveService batchSaveService;

    public BatchInsertController(BatchSaveService batchSaveService) {
        this.batchSaveService = batchSaveService;
    }

    @GetMapping("/batch/save")
    public void saveDataUsingBatch(@RequestParam int batchSize) {
        batchSaveService.saveDataUsingBatch(batchSize);
    }

}
