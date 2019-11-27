package lv.nixx.poc.db;

import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TablePrinter {

    private static final Logger LOG = LoggerFactory.getLogger("TablePrinter");
    private String tableName;

    public TablePrinter(String tableName) {
        this.tableName = tableName;
    }

    public void print() {

        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:derbyDB");

        try {
            Connection connection = dataSource.getConnection();

            Statement statement = connection.createStatement();

            final ResultSet rs = statement.executeQuery("select * from " + this.tableName);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            ColumnInfo[] columnInfos = new ColumnInfo[columnCount];

            for (int c = 1; c <= columnCount; c++) {
                String columnName = metaData.getColumnName(c);
                columnInfos[c - 1] = new ColumnInfo(columnName);
            }

            List<String[]> values = new ArrayList<>();

            while (rs.next()) {
                String[] v = new String[columnCount];
                for (int c = 1; c <= columnCount; c++) {
                    String cValue = rs.getString(c);
                    int pos = c - 1;
                    v[pos] = cValue;
                    columnInfos[pos].updateMax(cValue);
                }
                values.add(v);
            }

            String leadingTabs = "\t\t";

            String header = Stream.of(columnInfos)
                    .map(ColumnInfo::getHeader)
                    .collect(Collectors.joining("|"));

            StringJoiner outRows = new StringJoiner("\n");
            outRows.add(leadingTabs + header);

            values.forEach(row -> {
                StringJoiner sj = new StringJoiner("|");
                for (int c = 0; c < row.length; c++) {
                    sj.add(columnInfos[c].formatValue(row[c]));
                }
                outRows.add(leadingTabs + sj.toString());
            });

            LOG.info("{} Table [{}] content \n{}", "\n", this.tableName, outRows);

        } catch (Exception ex) {
            LOG.error("Can't print table [{}] content, error [{}]", this.tableName, ex.getMessage() );
        }

    }


    @ToString
    static class ColumnInfo {
        String name;
        int maxSize;

        ColumnInfo(String name) {
            this.name = name;
            this.maxSize = name.length();
        }

        void updateMax(String value) {
            if (value==null) {
                return;
            }
            int max = Math.max(value.length(), name.length());
            if (max > maxSize) {
                maxSize = value.length();
            }
        }

        String formatValue(String value) {
            return StringUtils.rightPad(value, maxSize);
        }

        String getHeader() {
            return StringUtils.rightPad(name, maxSize);
        }
    }


}
