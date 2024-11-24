package lv.nixx.poc.txs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//TODO Move to Spring JPA project
//TODO Add Transaction inside transaction sample Transactional.TxType.REQUIRES_NEW

@SpringBootApplication
@EnableJpaRepositories
public class TxSandboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(TxSandboxApplication.class, args);
	}

}
