package lv.nixx.poc.db;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUtils {

    private static final Logger LOG = LoggerFactory.getLogger("DBUtils");

    public static void showTableContent(String tableName) throws Exception {

		EmbeddedDataSource dataSource = new EmbeddedDataSource();
		dataSource.setDatabaseName("memory:derbyDB");

        Connection connection = dataSource.getConnection();

        Statement statement = connection.createStatement();

        final ResultSet rs = statement.executeQuery("select * from " + tableName);
        while (rs.next()) {
            LOG.info(rs.getString(1) + ":" + rs.getString(2));
        }



            //https://stackoverflow.com/questions/12423930/how-to-retrieve-the-datasource-used-by-a-persistence-unit-programmatically
    }

}
