package lv.nixx.poc.db.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Component;

@Component
public class StatisticsDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	final static DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	
	public List<?> getDailyStatistics() throws ParseException {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("sp_get_bugs_by_period");

        query.setParameter("start_date", sdf.parse("01.09.2017"));
        query.setParameter("end_date", sdf.parse("10.09.2017"));

        return query.getResultList();
	}
	
	

}
