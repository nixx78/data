package lv.nixx.poc.spring.data;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.derby.jdbc.ClientDataSource;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"lv.nixx.poc.spring.data.repository", "lv.nixx.poc.spring.data.repository.txn"})
@ComponentScan("lv.nixx.poc.spring.data")
public class JPAConfiguration {

	@Bean
	public DataSource dataSource() {
		ClientDataSource ds = new ClientDataSource();
		ds.setDatabaseName("SpringSampleDB");
		ds.setCreateDatabase("create");
		ds.setServerName("localhost");
		ds.setPortNumber(1527);
		
		return ds;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		jpaAdapter.setDatabasePlatform("org.hibernate.dialect.DerbyTenSevenDialect");
		jpaAdapter.setGenerateDdl(false);
		jpaAdapter.setShowSql(false);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource());
		factory.setJpaVendorAdapter(jpaAdapter);
		factory.afterPropertiesSet();

		return factory.getObject();
	}
	
	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setDataSource(dataSource());
		return txManager;
	}


}
