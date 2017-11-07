package lv.nixx.poc.db.dao;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lv.nixx.poc.db.application.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
public class StatisticsDAO_Test {
	
	@Autowired
	StatisticsDAO statisticsDAO;
	
	@Test
	public void test() throws ParseException {
		statisticsDAO.getDailyStatistics().forEach(System.out::println);
	}

}
