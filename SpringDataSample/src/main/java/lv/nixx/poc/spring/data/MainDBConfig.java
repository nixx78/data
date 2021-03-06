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
@EnableJpaRepositories(basePackages = {
		"lv.nixx.poc.spring.data.repository.main" }, 
		entityManagerFactoryRef = "mainEntityManager"
)

@ComponentScan("lv.nixx.poc.spring.data.*")
public class MainDBConfig {

	@Autowired
	private Environment env;

	@Bean
	public DataSource mainDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("main_db.jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("main_db.jdbc.url"));
		dataSource.setUsername(env.getProperty("main_db.user"));
		dataSource.setPassword(env.getProperty("main_db.pass"));

		return dataSource;
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean mainEntityManager() {

		HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("main_db.hibernate.hbm2ddl.auto"));
		properties.put("hibernate.dialect", env.getProperty("main_db.hibernate.dialect"));
		properties.put("hibernate.show_sql", env.getProperty("main_db.hibernate.show_sql"));

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(mainDataSource());
		factory.setJpaVendorAdapter(jpaAdapter);
		factory.setJpaPropertyMap(properties);
		factory.setPackagesToScan(new String[] { "lv.nixx.poc.spring.data.domain.main" });

		return factory;
	}

	@Bean
	@Primary
	public PlatformTransactionManager transactionManager(@Qualifier("mainEntityManager") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
