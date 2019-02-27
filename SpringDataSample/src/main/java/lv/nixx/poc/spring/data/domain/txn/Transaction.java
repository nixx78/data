package lv.nixx.poc.spring.data.domain.txn;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="TRANSACTIONS")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", nullable=false)
	private Long id;
	
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
    
    @Column(name="account", nullable=false)
    private String account;

	public Transaction(LocalDateTime date, BigDecimal amount, String description, Currency currency, String account) {
		this.date = date;
		this.amount = amount;
		this.description = description;
		this.currency = currency;
		this.account = account;
	}
    
    
	
}
