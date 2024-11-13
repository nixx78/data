package lv.nixx.poc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

@RestController
public class DBDataSourceController {

    private static final Logger log = LoggerFactory.getLogger(DBDataSourceController.class);

    private final DataSource dataSource;

    public DBDataSourceController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/test/datasource/alpha")
    public void testAlphaDataSource() {

        try (Connection connection = dataSource.getConnection()) {
            log.info("Start connection check, connection: {}", connection.toString());
            TimeUnit.SECONDS.sleep(60L);
            log.info("Finish connection check");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }
}
