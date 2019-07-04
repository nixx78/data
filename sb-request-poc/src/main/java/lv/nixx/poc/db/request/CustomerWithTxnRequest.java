package lv.nixx.poc.db.request;


import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.CustomSql;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import lv.nixx.poc.db.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CustomerWithTxnRequest extends GenericCustomerRequest<Customer> {

    private static Logger LOG = LoggerFactory.getLogger(CustomerWithTxnRequest.class);

    private String firstName;
    private String lastName;

    public static CustomerWithTxnRequest create() {
        return new CustomerWithTxnRequest();
    }

    public CustomerWithTxnRequest withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CustomerWithTxnRequest withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


    public List<Customer> execute(EntityManager entityManager) {


        SelectQuery q = new SelectQuery()
                .addCustomColumns(
                        new CustomSql("id"),
                        new CustomSql("firstName"),
                        new CustomSql("lastName"),
                        new CustomSql("type_id")
                )
                .addFromTable(customerTable)
                .validate();

        // We can add condition with column just as text
        Optional.ofNullable(firstName).map(t -> q.addCondition(BinaryCondition.like(firstNameCustomerCol, t)));

        // We can add condition using column object
        Optional.ofNullable(lastName).map(t -> q.addCondition(BinaryCondition.like(lastNameCustomerCol, t)));


        LOG.info("Incoming parameters: firstName [{}] lastName [{}]", firstName, lastName);
        LOG.info("Sql request [{}]", q);

        return entityManager.createNativeQuery(q.toString(), Customer.class).getResultList();
    }

}
