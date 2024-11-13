package lv.nixx.poc.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class AppConfig {

    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public ApplicationRunner validateDataSource(DataSource alphaDataSource, DataSource betaDataSource) {
        return args -> {
            try (Connection connection = alphaDataSource.getConnection()) {
            } catch (Exception ex) {
                log.error("Problem with connection to AlphaDataSource", ex);
            }
        };
    }
}
