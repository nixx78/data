package lv.nixx.poc.model;

import java.util.Collection;

public record SaveResult(Long accountId, String accountName, Collection<Long> createdTxnIds) {
}
