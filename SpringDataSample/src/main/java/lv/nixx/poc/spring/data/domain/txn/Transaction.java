package lv.nixx.poc.spring.data.domain.txn;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="TRANSACTIONS")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class Transaction extends AbstractPersistable<Long> {
	
    @Column(name="date", nullable=false)
	private LocalDateTime date;
    
    @Column(name="amount", nullable=false)
	private BigDecimal amount;
    
    @Column(name="descr", nullable=false)
	private String description;
	
    @OneToOne( targetEntity=Currency.class,  optional=false)
    @JoinColumn(name = "currency_code")
	private Currency currency;
	
}
