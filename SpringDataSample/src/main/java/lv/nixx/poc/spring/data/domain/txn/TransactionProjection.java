package lv.nixx.poc.spring.data.domain.txn;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;

public interface TransactionProjection {
	
	long getId();
	
	 @Value("#{target.currency.getAlphaCode()}")
	 String getCurrency();
	 
	 BigDecimal getAmount();
	 
	 default String getFormattedAmount() {
		 return getAmount() + getCurrency();
	 }
	 
	String getDescription();

}
