package lv.nixx.poc.spring.data.repository.txn;

import org.springframework.data.repository.CrudRepository;

import lv.nixx.poc.spring.data.domain.txn.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, String> {

}
