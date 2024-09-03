package lv.nixx.poc.poc.util;

import lv.nixx.poc.orm.AccountType;
import lv.nixx.poc.orm.TransactionType;
import lv.nixx.poc.repository.AccountTypeRepository;
import lv.nixx.poc.repository.TransactionTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Service
public class TypesLoader {

    private final AccountTypeRepository accountTypeRepository;
    private final TransactionTypeRepository transactionTypeRepository;

    private final AtomicBoolean isAlreadyLoaded = new AtomicBoolean(false);

    private Map<String, TransactionType> txnTypeByName;
    private Map<String, AccountType> accountTypeByName;

    public TypesLoader(AccountTypeRepository accountTypeRepository, TransactionTypeRepository transactionTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
        this.transactionTypeRepository = transactionTypeRepository;
    }

    public void loadTypes() {

        if (isAlreadyLoaded.get()) {
            return;
        }

        this.txnTypeByName = transactionTypeRepository.saveAll(List.of(
                        new TransactionType("type1"),
                        new TransactionType("type2")
                ))
                .stream()
                .collect(toMap(TransactionType::getTypeName, Function.identity()));

        this.accountTypeByName = accountTypeRepository.saveAll(List.of(
                        new AccountType("CurrentAccount"),
                        new AccountType("SavingAccount"),
                        new AccountType("CardAccount")
                )).stream()
                .collect(toMap(AccountType::getName, Function.identity()));

        isAlreadyLoaded.set(true);
    }

    public TransactionType getType1() {
        return txnTypeByName.get("type1");
    }

    public TransactionType getType2() {
        return txnTypeByName.get("type2");
    }

    public AccountType getCurrentAccount() {
        return accountTypeByName.get("CurrentAccount");
    }
    public AccountType getSavingAccount() {
        return accountTypeByName.get("SavingAccount");
    }

    public AccountType getCardAccount() {
        return accountTypeByName.get("CardAccount");
    }

}