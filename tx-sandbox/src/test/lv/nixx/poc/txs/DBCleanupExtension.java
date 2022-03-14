package lv.nixx.poc.txs;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

public class DBCleanupExtension implements BeforeEachCallback {

    private static final Logger LOG = LoggerFactory.getLogger(DBCleanupExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        ApplicationContext ctx = SpringExtension.getApplicationContext(extensionContext);
        JdbcTemplate t = new JdbcTemplate(ctx.getBean(DataSource.class));

        t.update("delete from TXN_TABLE");
        t.update("delete from BALANCE_TABLE");

        LOG.info("Table cleanup complete");
    }

}
