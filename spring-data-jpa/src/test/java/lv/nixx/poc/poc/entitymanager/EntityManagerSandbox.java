package lv.nixx.poc.poc.entitymanager;


import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lv.nixx.poc.orm.AccountType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class EntityManagerSandbox {

    @Autowired
    private EntityManager entityManager;

    @Test
    void mergeTest() {
        AccountType accountType = new AccountType("type1");

        System.out.println("----- Before MERGE ----- ");
        Long createdId = entityManager.merge(accountType).getId();

        System.out.println("Created customer:" + createdId);
        entityManager.flush();

        System.out.println("-----  Method end ----- ");
    }

    @Test
    void persistTest() {
        AccountType accountType = new AccountType("type1");

        System.out.println("----- Before PERSIST ----- ");
        entityManager.persist(accountType);

        System.out.println("Customer:" + accountType.getId());
        entityManager.flush();

        System.out.println("-----  Method end ----- ");
    }

    @Test
    void mergeWithSelect() {
        System.out.println("Merge with SELECT");
        AccountType c = new AccountType("type1");
        entityManager.persist(c);
        entityManager.flush();

        System.out.println("----- After flush -----");

        entityManager.clear();

        AccountType detached = new AccountType("type2");
        detached.setId(c.getId());

        entityManager.merge(detached);
        entityManager.flush();
        System.out.println("----- After MERGE -----");
    }

}
