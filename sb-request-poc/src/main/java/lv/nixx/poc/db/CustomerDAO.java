package lv.nixx.poc.db;


import lv.nixx.poc.db.domain.Customer;
import lv.nixx.poc.db.domain.CustomerWithType;
import lv.nixx.poc.db.request.CompositeCustomerRequest;
import lv.nixx.poc.db.request.CustomerRequest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collection;

public class CustomerDAO {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

    public Collection<Customer> findCustomersByFirstLastName(String firstName, String lastName) {
        final EntityManager em = factory.createEntityManager();

        try {
            return CustomerRequest.create()
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .execute(em);

        } finally {
            em.close();
        }
    }

    public Collection<CustomerWithType>  findCustomersWithType(String firstName, String lastName, Collection<String> types) {

        final EntityManager em = factory.createEntityManager();

        try {
            return CompositeCustomerRequest.create()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withType(types)
                .execute(em);

        } finally {
            em.close();
        }

    }


}
