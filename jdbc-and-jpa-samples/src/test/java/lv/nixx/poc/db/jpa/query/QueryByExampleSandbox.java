package lv.nixx.poc.db.jpa.query;

import lv.nixx.poc.db.domain.Person;
import lv.nixx.poc.db.domain.PersonExtension;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

class QueryByExampleSandbox {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("test.unit");

    @BeforeEach
    void cleanUp() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction txn = em.getTransaction();
        txn.begin();
        em.createQuery("delete from Person").executeUpdate();
        txn.commit();
    }

    @Test
    void queryByExampleTest() {

        PersonExtension ext = new PersonExtension("line1", "line2");

        Person p = new Person("N1", "S1", ext);
        p.addAlias("Alias1");
        p.addAlias("Alias2");
        p.addAlias("Alias3");

        try (Session session = (Session) emf.createEntityManager(); session) {

            EntityTransaction txn = session.getTransaction();
            txn.begin();

            session.merge(p);
            txn.commit();

            Person search = new Person("N1", "S1", ext);
            Example example = Example.create(search);

            Criteria criteria = session.createCriteria(Person.class).add(example);
            List<Person> list = criteria.list();

            // Why in result is 3 responses ?!
            System.out.println(list);
        }


    }
}
