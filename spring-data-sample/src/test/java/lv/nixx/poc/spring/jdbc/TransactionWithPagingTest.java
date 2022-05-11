package lv.nixx.poc.spring.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TransactionWithPagingTest {

    @Autowired
    private TransactionRepositoryWithPaging transactionRepository;

    @Test
    void pagingSortingTest() {
        insertTestData();

        Page<Transaction> p = transactionRepository.findAll(PageRequest.of(1, 5));

        Collection<Transaction> firstPage = StreamSupport.stream(p.spliterator(), false).toList();

        BigDecimal minAmount = firstPage.stream().map(Transaction::getAmount).min(BigDecimal::compareTo).orElse(null);
        BigDecimal maxAmount = firstPage.stream().map(Transaction::getAmount).max(BigDecimal::compareTo).orElse(null);

        assertAll(
                () -> assertEquals(5, firstPage.size()),
                () -> assertThat(minAmount, comparesEqualTo(BigDecimal.valueOf(5))),
                () -> assertThat(maxAmount, comparesEqualTo(BigDecimal.valueOf(9)))
        );

        Page<Transaction> sortedPage = transactionRepository.findAll(PageRequest.of(0, 10,
                Sort.Direction.ASC, "id"));

        assertEquals(10, sortedPage.getSize());

        Collection<Transaction> sortedTxn = StreamSupport.stream(sortedPage.spliterator(), false).toList();

        assertEquals(10, sortedTxn.size());
    }

    void insertTestData() {
        transactionRepository.deleteAll();

        int count = 100;
        LocalDateTime now = LocalDateTime.now();

        Collection<Transaction> txns = new ArrayList<>();
        for (int i = 0; i < count; i++) {

            String accId = "ACC1";
            if ((i % 5) == 0) {
                accId = "ACC5";
            }

            txns.add(new Transaction()
                    .setCurrency("USD")
                    .setDate(now)
                    .setAccountId(accId)
                    .setDescription("Description" + i)
                    .setAmount(BigDecimal.valueOf(i)));
        }
        transactionRepository.saveAll(txns);
    }

}
