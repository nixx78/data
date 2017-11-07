package lv.nixx.poc.db.application;

import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import oracle.jdbc.pool.OracleDataSource;

@Configuration
@ComponentScan(basePackages = {"lv.nixx.poc.db.application", "lv.nixx.poc.db.dao"})
@EnableTransactionManagement
public class AppConfig {
	
	@Bean
	public DataSource dataSource() throws SQLException {
		OracleDataSource ds = new OracleDataSource();
		ds.setURL("jdbc:oracle:thin:@localhost:1521/sanbox_db");
		ds.setUser("nixx");
		ds.setPassword("nixx");
		return ds;
	}
	@Bean
	public EntityManagerFactory entityManagerFactory() throws Throwable {

		EclipseLinkJpaVendorAdapter jpaAdapter = new EclipseLinkJpaVendorAdapter();
		jpaAdapter.setGenerateDdl(false);
		jpaAdapter.setShowSql(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setPersistenceUnitName("test.unit");
		factory.setDataSource(dataSource());
		factory.setJpaVendorAdapter(jpaAdapter);
		factory.setPackagesToScan("lv.nixx.poc.db.dao");
		factory.setLoadTimeWeaver(loadTimeWeaver());

		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) throws SQLException {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

	@Bean
	public InstrumentationLoadTimeWeaver loadTimeWeaver() throws Throwable {
		InstrumentationLoadTimeWeaver loadTimeWeaver = new InstrumentationLoadTimeWeaver();
		return loadTimeWeaver;
	}

}
