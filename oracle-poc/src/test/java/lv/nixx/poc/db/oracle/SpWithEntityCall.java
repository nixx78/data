package lv.nixx.poc.db.oracle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

public class SpWithEntityCall {
	
	final static DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	public static void main(String[] args) throws ParseException, InterruptedException {
		
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle-poc");
        EntityManager em = factory.createEntityManager();

        StoredProcedureQuery query = em.createNamedStoredProcedureQuery("get_bugs_by_period");
        query.setParameter("start_date", sdf.parse("01.09.2017"));
        query.setParameter("end_date", sdf.parse("10.09.2017"));

        List<?> resultList = query.getResultList();
        resultList.forEach(System.out::println);
	}

}
