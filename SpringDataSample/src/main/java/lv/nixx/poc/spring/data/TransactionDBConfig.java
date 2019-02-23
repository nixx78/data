package lv.nixx.poc.spring.data;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.derby.jdbc.ClientDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = {"lv.nixx.poc.spring.data.repository.txn"},
		entityManagerFactoryRef = "txnEntityManager"
	)

public class TransactionDBConfig {

	@Bean(name = "transactionDB")
	public DataSource transactionDBSource() {
		ClientDataSource ds = new ClientDataSource();
		ds.setDatabaseName("TxnDB");
		ds.setCreateDatabase("create");
		ds.setServerName("localhost");
		ds.setPortNumber(1527);
		return ds;
	}

	@Bean(name="txnEntityManager")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		jpaAdapter.setDatabasePlatform("org.hibernate.dialect.DerbyTenSevenDialect");
		jpaAdapter.setGenerateDdl(false);
		jpaAdapter.setShowSql(false);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(transactionDBSource());
		factory.setJpaVendorAdapter(jpaAdapter);
		factory.setPackagesToScan(new String[] {"lv.nixx.poc.spring.data.domain.txn"});

		return factory;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(@Qualifier("txnEntityManager") EntityManagerFactory entityManagerFactory ) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
