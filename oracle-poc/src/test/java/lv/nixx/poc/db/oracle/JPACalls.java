package lv.nixx.poc.db.oracle;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

import org.junit.Test;

public class JPACalls {
	
	final static DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	@Test
	public void createSpCall() throws ParseException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle-poc");
        EntityManager em = factory.createEntityManager();
        StoredProcedureQuery query = em.createNamedStoredProcedureQuery("sp_get_bugs_by_period");

        query.setParameter("start_date", sdf.parse("01.09.2017"));
        query.setParameter("end_date", sdf.parse("10.09.2017"));

        List<?> resultList = query.getResultList();
        resultList.forEach(System.out::println);
	}
	
	@Test
	public void functionCallUsingNativeQuery() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle-poc");
        EntityManager em = factory.createEntityManager();

        BigDecimal count = (BigDecimal)em.createNativeQuery("SELECT sandbox_sp.get_bugs_count(?1) FROM DUAL")
                .setParameter(1, 1)
                .getSingleResult();
                
        System.out.println(count);
		
	}

}
