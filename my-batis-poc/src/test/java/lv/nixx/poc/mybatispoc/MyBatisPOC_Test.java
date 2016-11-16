package lv.nixx.poc.mybatispoc;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import lv.nixx.poc.mybatis.ConnectionFactory;
import lv.nixx.poc.mybatis.User;
import lv.nixx.poc.mybatis.UserDAO;

public class MyBatisPOC_Test {

	static SqlSession session = null;

	@BeforeClass
	public static void init() throws FileNotFoundException {
		session = ConnectionFactory.getSession().openSession(true);
		ScriptRunner sr = new ScriptRunner(session.getConnection());
		
		InputStream resourceAsStream = ClassLoader.getSystemResourceAsStream("db_init.sql");
		sr.runScript(new InputStreamReader(resourceAsStream));
	}
	
	@AfterClass
	public static void tearDown() {
		if (session != null) {
			session.close();
		}
	}

	@Test
	public void makeCRUDOperations() throws Exception {

		UserDAO userDao = session.getMapper(UserDAO.class);
		userDao.deleteAllUser();

		User vo = new User();
		vo.setAddress("Test");
		vo.setEmail("test@gmail.com");
		vo.setFullName("Full Name");
		vo.setMobile("12411515");

		userDao.сreateUser(vo);
		System.out.println("Created user: " + vo);

		vo = userDao.getUserById(vo.getId());
		
		assertNotNull(vo);

		vo.setAddress("Test Updated11 Address");
		vo.setEmail("testupdated@gmail.com");
		vo.setFullName("Updated Full Name");
		vo.setMobile("1241151511");

		userDao.updateUser(vo);

		List<User> users = userDao.getAllUsers();
		assertEquals(1, users.size());

		System.out.println("----- AllUsers -----");
		for (User usr : users) {
			System.out.println(usr);
		}
		userDao.deleteUser(vo);
		
		assertEquals(0, userDao.getUserCount());
	}

}
