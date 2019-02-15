package lv.nixx.poc.spring.data.domain.txn;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TRANSACTIONS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper=true)
public class Transaction extends AbstractPersistable<Long> {
	
    @Column(name="date", nullable=false)
	private LocalDateTime date;
    
    @Column(name="amount", nullable=false)
	private BigDecimal amount;
    
    @Column(name="descr", nullable=false)
    @Setter
	private String description;
	
    @ManyToOne( targetEntity=Currency.class)
    @JoinColumn(name = "currency_code")
	private Currency currency;
	
}
