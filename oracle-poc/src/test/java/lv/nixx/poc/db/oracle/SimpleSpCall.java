package lv.nixx.poc.db.oracle;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;

import oracle.jdbc.OracleTypes;

public class SimpleSpCall {

	@Test
	public void createSpCall() {

		try {
			Connection con = createConnection();

			final DateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

			Date fromDate = new java.sql.Date(simpleDateFormat.parse("01.09.2017").getTime());
			Date toDate = new java.sql.Date(simpleDateFormat.parse("10.09.2017").getTime());
			
			try (CallableStatement cstmt = con.prepareCall("{call sandbox_sp.get_bugs_by_period(?,?,?)}")) {
				cstmt.setDate(1, fromDate);
				cstmt.setDate(2, toDate);
				cstmt.registerOutParameter(3, OracleTypes.CURSOR);

				cstmt.execute();
				ResultSet resultSet = cstmt.getObject(3, ResultSet.class);

				while (resultSet.next()) {
					System.out.println( resultSet.getDate("MONTH_DAY") + ":" + resultSet.getString("COUNT") + ":" + resultSet.getString("IDS"));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void functionCall() throws Exception {
		Connection con = createConnection();
		
		try (CallableStatement cstmt = con.prepareCall("{? = call sandbox_sp.get_bugs_count(?)}")) {
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.setInt(2, 1);
			cstmt.executeUpdate();
			int count= cstmt.getInt(1);
			System.out.println(count);
		}

	}

	private Connection createConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521/sanbox_db"; // sanbox_db == service name NOT SID
		Connection con = DriverManager.getConnection(url, "nixx", "nixx");
		System.out.println("Connected to database");
		return con;
	}

}
