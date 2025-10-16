package lv.nixx.poc.poc.jdbc;

import lv.nixx.poc.poc.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NamedParameterJdbcTemplateSandbox extends BaseTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void getByTypesTest() {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);

        Map<String, ?>[] batchArgs = new Map[]{
                Map.of("id", 1,
                        "name", "NameT1",
                        "type", "Type1"
                ),
                Map.of("id", 2,
                        "name", "NameT21",
                        "type", "Type2"
                ),
                Map.of("id", 3,
                        "name", "NameT22",
                        "type", "Type2"
                ),
                Map.of("id", 4,
                        "name", "NameT3",
                        "type", "Type3"
                )
        };

        template.update("INSERT INTO CUSTOMER_TBL (ID, sName,sType) VALUES(:id, :name,:type)", Map.of(
                "id", 100,
                "name", "NameT100",
                "type", "Type100"
        ));

        template.batchUpdate("INSERT INTO CUSTOMER_TBL (ID, sName,sType) VALUES(:id, :name,:type)", batchArgs);

        List<Map<String, Object>> dataAsMap = template.query("SELECT sName FROM CUSTOMER_TBL WHERE sType IN(:type)", Map.of(
                        "type", List.of("Type1", "Type2")
                ),
                new ColumnMapRowMapper());

        assertThat(dataAsMap).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(
                List.of(
                        Map.of("SNAME", "NameT1"),
                        Map.of("SNAME", "NameT21"),
                        Map.of("SNAME", "NameT22")
                )
        );

        List<String> names = template.queryForList("SELECT sName FROM CUSTOMER_TBL WHERE sType IN(:type)",
                Map.of("type", List.of("Type1", "Type2")), String.class);

        assertThat(names).isEqualTo(List.of("NameT1", "NameT21", "NameT22"));

        Long totalCount = template.queryForObject("SELECT count(*) FROM CUSTOMER_TBL", Map.of(), Long.class);

        assertThat(totalCount).isEqualTo(5L);

        List<Map<String, Object>> aggregates = template.query("SELECT sType, count(*) AS count FROM CUSTOMER_TBL GROUP BY sType", new ColumnMapRowMapper());

        assertEquals(4, aggregates.size());
    }

}
