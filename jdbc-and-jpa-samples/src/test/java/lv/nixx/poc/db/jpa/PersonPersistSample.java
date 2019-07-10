package lv.nixx.poc.db.jpa;

import lv.nixx.poc.db.TablePrinter;
import lv.nixx.poc.db.domain.Person;
import lv.nixx.poc.db.domain.PersonAdditionalField;
import lv.nixx.poc.db.domain.PersonExtension;
import lv.nixx.poc.db.domain.Task;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

public class PersonPersistSample {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

    private TablePrinter personPrinter = new TablePrinter("PERSON");
    private TablePrinter aliasTable = new TablePrinter("ALIAS_TABLE");
    private TablePrinter additionalFieldTable = new TablePrinter("PERSON_ADDITIONAL_FIELD");
    private TablePrinter taskTable = new TablePrinter("TASK");

    @Test
    public void personPersistTest() throws Exception {

        PersonExtension ext = new PersonExtension("line1", "line2");

        Person  p = new Person("Name1", "Surname1", ext);
        p.addAlias("Alias1");
        p.addAlias("Alias2");
        p.addAlias("Alias3");

        p.addAdditionalField(new PersonAdditionalField("p1_field1", "p1_field2"));
        p.addAdditionalField(new PersonAdditionalField("p1_field1", "p1_field2"));

        p.addTask(new Task("id1", "description1", "project1"));
        p.addTask(new Task("id2", "description2", "project2"));

        EntityManager em = factory.createEntityManager();
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


    }


}
