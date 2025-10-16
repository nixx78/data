package lv.nixx.poc.rest;

import lv.nixx.poc.orm.TransactionType;
import lv.nixx.poc.repository.TransactionTypeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionTypeCustomController {

    private final TransactionTypeRepository transactionTypeRepository;

    public TransactionTypeCustomController(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    @PostMapping("/transactionType")
    public TransactionType saveTransactionType(@RequestBody TransactionType transactionType) {
        return transactionTypeRepository.saveWithAuditable(transactionType);
    }

    @GetMapping("/transactionType")
    public List<TransactionType> getAllTypes() {
        return transactionTypeRepository.findAll();
    }

}
