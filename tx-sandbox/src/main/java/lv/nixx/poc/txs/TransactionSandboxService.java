package lv.nixx.poc.txs;

import lv.nixx.poc.txs.data.AppException;
import lv.nixx.poc.txs.data.BalanceRepository;
import lv.nixx.poc.txs.data.TransactionRepository;
import lv.nixx.poc.txs.data.model.Container;
import lv.nixx.poc.txs.data.model.Statistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class TransactionSandboxService {

    private static final Logger LOG = LoggerFactory.getLogger("TransactionSandboxService");

    @Autowired
    private TransactionRepository txnRepo;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private BalancePersistence balancePersistence;

    public Statistic saveAllInTransaction(Container c) {
        return new Execution().execute(c, balancePersistence::saveTxnAndBalanceTransactionalAnnotated);
    }

    public Statistic saveUsingTransactionTemplate(Container c) {
        return new TransactionTemplate(transactionManager).execute(
                t -> new Execution().execute(c, balancePersistence::saveWithoutTransaction)
        );
    }

    // In this case transaction not working, we call method from another method from the save class
    public Statistic saveInTxnInternalMethodCall(Container c) {
        return new Execution().execute(c, balancePersistence::saveCallingInternalMethod);
    }

    class Execution {
        Statistic execute(Container c, Function<Container, Container> f) {
            Statistic stat = new Statistic();
            stat.setBeforeOperation(getTableContent());

            try {
                f.apply(c);
            } catch (Exception ex) {
                stat.setStatus("FAIL");
                stat.setError(ex.getMessage());
                throw new AppException(stat, ex.getMessage());
            } finally {
                stat.setData(Map.of(
                        "balance", balanceRepository.findAll(),
                        "txn", txnRepo.findAll()
                ));

                stat.setAfterOperation(getTableContent());
            }

            return stat;
        }

        List<Statistic.TableInfo> getTableContent() {
            return List.of(
                    new Statistic.TableInfo("balance", balanceRepository.count()),
                    new Statistic.TableInfo("txn", txnRepo.count())
            );
        }

    }

    //TODO Add Transaction inside transaction sample Transactional.TxType.REQUIRES_NEW


}
