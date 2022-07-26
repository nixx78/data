package lv.nixx.poc.db.jpa;

import lv.nixx.poc.db.TablePrinter;
import lv.nixx.poc.db.domain.Person;
import lv.nixx.poc.db.domain.PersonAdditionalField;
import lv.nixx.poc.db.domain.PersonExtension;
import lv.nixx.poc.db.domain.Task;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Map;

public class PersonPersistTest {

    private static final Logger LOG = LoggerFactory.getLogger(PersonPersistTest.class);

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

    private TablePrinter personPrinter = new TablePrinter("PERSON");
    private TablePrinter aliasTable = new TablePrinter("ALIAS_TABLE");
    private TablePrinter additionalFieldTable = new TablePrinter("PERSON_ADDITIONAL_FIELD");
    private TablePrinter taskTable = new TablePrinter("TASK");

    private EntityManager em = factory.createEntityManager();

    @Test
    public void personPersistTest() {

        PersonExtension ext = new PersonExtension("line1", "line2");

        Person  p = new Person("Name1", "Surname1", ext);
        p.addAlias("Alias1");
        p.addAlias("Alias2");
        p.addAlias("Alias3");

        p.addAdditionalField(new PersonAdditionalField("p1_field1", "p1_field2"));
        p.addAdditionalField(new PersonAdditionalField("p1_field1", "p1_field2"));

        p.addTask(new Task("id1", "description1", "project1"));
        p.addTask(new Task("id2", "description2", "project2"));

        em.getTransaction().begin();

        em.persist(p);

        em.getTransaction().commit();

        personPrinter.print();
        aliasTable.print();
        additionalFieldTable.print();
        taskTable.print();

        Person person = em.find(Person.class, p.getId());

        Map<String, Task> tasks = person.getTasks();
        System.out.println(tasks);

        person.setSurname("New name");
        em.getTransaction().begin();
        em.merge(person);
        em.getTransaction().commit();

        LOG.info("After person update");

        personPrinter.print();

    }

    @Test(expected = OptimisticLockException.class)
    public void optimisticLockExceptionSample() {

        // Insert new Person
        Person  p = new Person("Name1", "Surname1", null);
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();

        // Detach from Persistence context
        em.detach(p);

        long id = p.getId();
        LOG.info("After first save, person id [{}] version [{}]", id, p.getVersion());

        // Find and change Person
        Person t = em.find(Person.class, id);
        t.setSurname("New surname");
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
        LOG.info("Version [{}] after update", t.getVersion());

        personPrinter.print();

        // Try to update initial Person (with old version)
        p.setSurname("Another new surname");
        LOG.info("Try to save with Person with version [{}]", p.getVersion());
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
    }


}
