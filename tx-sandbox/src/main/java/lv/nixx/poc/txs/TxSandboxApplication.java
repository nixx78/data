package lv.nixx.poc.txs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TxSandboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(TxSandboxApplication.class, args);
	}

}
