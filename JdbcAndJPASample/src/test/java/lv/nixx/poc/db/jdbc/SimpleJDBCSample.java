package lv.nixx.poc.db.jdbc;

import static org.junit.Assert.*;

import java.sql.*;
import java.util.Arrays;

import org.junit.Test;

public class SimpleJDBCSample {
	

	@Test
	public void addBatch() throws Exception {
		// Создадим экземпляр драйвера дерби
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		Connection connection = DriverManager.getConnection("jdbc:derby:" + "derbyDB;create=true", null);
		connection.setAutoCommit(false);
		
		// это один из способов как получить соединение начиная с 2.0 версии JDBC, достаточно только использовать подходящий класс бызы данных
//		EmbeddedDataSource40 dataSource = new EmbeddedDataSource40();
//		dataSource.setDatabaseName("derbyDB");
//		
//		Connection connection = dataSource.getConnection();
//		Statement statement = connection.createStatement();

		try (Statement statement = connection.createStatement()) {
			
			// Удалим (на всякий случай) а затем создадим таблицу
			try {
				statement.execute("Drop table Persons");
			} catch (SQLException e){
				System.err.println(e); // Это абсолютно нормальная ситуация, если таблицы нет, ошибку можно игнорировать
			} 
			statement.execute("Create table Persons (Id integer, Name varchar(25), Surname varchar(25), State varchar(25) )");
			
			// Поместим выражения в батч, затем выполним его
			statement.addBatch("Insert into Persons (id, name, surname, state) values(1, 'Name1', 'Surname1', 'Empty')");
			statement.addBatch("Insert into Persons (id, name, surname, state) values(2, 'Name2', 'Surname2', 'Upd')");
			statement.addBatch("Insert into Persons (id, name, surname, state) values(3, 'Name3', 'Surname3', 'Upd')");
			statement.addBatch("Insert into Persons (id, name, surname, state) values(4, 'Name4', 'Surname4', 'Empty')");
			
			int[] batchResult = statement.executeBatch();
			System.out.println("Batch execution result : " + Arrays.toString(batchResult));
			assertTrue(Arrays.equals(new int[]{1,1,1,1}, batchResult));
			
			// Запросим и выведем на экран все записи в таблице c пустым статусом
			ResultSet rs = statement.executeQuery("Select * from Persons where state='Empty'");
			while (rs.next()) {
				System.out.println(rs.getInt(1) + ":" + rs.getString(2) + ":" + rs.getString(3) + ":" + rs.getString(4));
			}
			connection.rollback();
		} finally {

			if (connection != null) {
				connection.close();
			}
		}
	}

}
