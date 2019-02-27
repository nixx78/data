package lv.nixx.sping.jdbc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcTemplateSandbox extends GenericJdbcTest {

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	@Before
	public void init() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Test
	public void queryForObjectSample() {
		Long cnt = jdbcTemplate.queryForObject("select count(*) from TRANSACTIONS", Long.class);
		System.out.println("Transactions count 'All':" + cnt);
	}

	@Test
	public void namedParameterJdbcTemplateSample() {
		String sql = "select count(*) from TRANSACTIONS where currency_code = :currency_code";
		Map<String, String> namedParameters = Collections.singletonMap("currency_code", "USD");
		Integer cnt = this.namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);

		System.out.println("Transactions count 'USD':" + cnt);
	}
	
	@Test
	public void queryForListSample() {
		List<Map<String, Object>> lst = jdbcTemplate.queryForList("select * from Transactions");

		// 1 row - 1 map
		lst.forEach(System.out::println);
	}
	
	@Test
	public void batchUpdateSample() {
		
	    List<Object[]> batch = Arrays.asList(
	    			new Object[]{"descr_acc2", "account2"},
	    			new Object[]{"descr_acc4", "account4"}
	    		);
        
        int[] updateCounts = jdbcTemplate.batchUpdate("update Transactions set descr = ? where account = ?", batch);
        System.out.println(Arrays.toString(updateCounts));
		
	}


}
