package lv.nixx.poc.db.request;


import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.CustomCondition;
import com.healthmarketscience.sqlbuilder.CustomSql;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lv.nixx.poc.db.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CustomerRequest {

    private static Logger LOG = LoggerFactory.getLogger(CustomerRequest.class);

    private String firstName;
    private String lastName;

    private DbTable customerTable;

    public CustomerRequest() {
        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();

        customerTable = schema.addTable("Customer");
    }

    public static CustomerRequest create() {
        return new CustomerRequest();
    }

    public CustomerRequest withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CustomerRequest withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


    public List<Customer> execute(EntityManager entityManager) {

        DbColumn lastNameColumn = customerTable.addColumn("lastName");

        SelectQuery q = new SelectQuery()
                .addCustomColumns(
                        new CustomSql("t0.id"),
                        new CustomSql("t0.firstName"),
                        new CustomSql("t0.lastName"),
                        new CustomSql("t0.type_id")
                )
                .addFromTable(customerTable)
                .validate();

        // We can add condition with column just as text
        Optional.ofNullable(firstName).map(t -> q.addCondition(new CustomCondition("t0.firstName = '" + t + "'")));

        // Wer can add condition using column object
        Optional.ofNullable(lastName).map(t -> q.addCondition(BinaryCondition.equalTo(lastNameColumn, t)));


        LOG.info("Incoming parameters: firstName [{}] lastName [{}]", firstName, lastName);
        LOG.info("Sql request [{}]", q);

        return entityManager.createNativeQuery(q.toString(), Customer.class).getResultList();
    }

}
