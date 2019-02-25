package lv.nixx.poc.spring.data;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("file:./src/main/resources/application.properties")
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = {"lv.nixx.poc.spring.data.repository.txn"},
		entityManagerFactoryRef = "txnEntityManager"
	)
@ComponentScan(basePackages="lv.nixx.poc.spring.jdbc")
public class TransactionDBConfig {
	
	@Autowired
	private Environment env;

	@Bean(name = "transactionDB")
	public DataSource transactionDBSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("txn_db.jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("txn_db.jdbc.url"));
		dataSource.setUsername(env.getProperty("txn_db.user"));
		dataSource.setPassword(env.getProperty("txn_db.pass"));

		return dataSource;
	}

	@Bean(name="txnEntityManager")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
		HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("txn_db.hibernate.hbm2ddl.auto"));
		properties.put("hibernate.dialect", env.getProperty("txn_db.hibernate.dialect"));
		properties.put("hibernate.show_sql", env.getProperty("txn_db.hibernate.show_sql"));

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(transactionDBSource());
		factory.setJpaVendorAdapter(jpaAdapter);
		factory.setJpaPropertyMap(properties);
		factory.setPackagesToScan(new String[] {"lv.nixx.poc.spring.data.domain.txn"});

		return factory;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(@Qualifier("txnEntityManager") EntityManagerFactory entityManagerFactory ) {
		return new JpaTransactionManager(entityManagerFactory);
	}


}
