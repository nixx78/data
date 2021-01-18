package lv.nixx.samples.json.jackson;

import static lv.nixx.samples.json.domain.Currency.EUR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import lv.nixx.samples.json.ObjectMapperService;
import org.junit.Test;

import lv.nixx.samples.json.domain.Transaction;
import lv.nixx.samples.json.domain.Txns;

public class SetJacksonTest {

    private ObjectMapperService objectMapper = new ObjectMapperService();

    @Test
    public void setTest() throws Exception {

        Txns txns = new Txns();
        txns.setTransactions(Set.of(
                new Transaction(10, 1, BigDecimal.valueOf(10.00), null, EUR),
                new Transaction(20, 2, BigDecimal.valueOf(20.00), null, EUR),
                new Transaction(30, 3, BigDecimal.valueOf(30.00), null, EUR),
                new Transaction(40, 4, BigDecimal.valueOf(40.00), null, EUR),
                new Transaction(50, 5, BigDecimal.valueOf(50.00), null, EUR)
                )
        );

        String json = objectMapper.writeValueAsString(txns);
        assertNotNull(json);

        Txns actualTxns = objectMapper.readValue(json, Txns.class);

        assertNotNull(actualTxns);
        Set<Transaction> transactions = actualTxns.getTransactions();
        assertEquals(5, transactions.size());

        final HashSet<Integer> toBeDeletedList = new HashSet<>(Arrays.asList(30, 40));

        Collection<Integer> sortedAndRemoved = transactions.stream()
                .sorted(Comparator.comparing(Transaction::getPos))
                .map(Transaction::getId)
                .filter(t -> !toBeDeletedList.contains(t))
                .collect(Collectors.toList());

        System.out.println(sortedAndRemoved);

        assertThat(sortedAndRemoved, containsInAnyOrder(10, 20, 50));

    }

}

