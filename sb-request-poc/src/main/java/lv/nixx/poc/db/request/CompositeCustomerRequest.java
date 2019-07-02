package lv.nixx.poc.db.request;


import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.CustomCondition;
import com.healthmarketscience.sqlbuilder.InCondition;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lv.nixx.poc.db.mapping.CustomerWithType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CompositeCustomerRequest extends GenericCustomerRequest<CustomerWithType> {

    private static Logger LOG = LoggerFactory.getLogger(CompositeCustomerRequest.class);

    private String firstName;
    private String lastName;
    private Collection<String> type;

    private DbTable customerType;

    public CompositeCustomerRequest() {
         customerType = addTableToSpec("CUSTOMER_TYPE", "ct");
    }

    public static CompositeCustomerRequest create() {
        return new CompositeCustomerRequest();
    }

    public CompositeCustomerRequest withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CompositeCustomerRequest withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CompositeCustomerRequest withType(Collection<String> type) {
        this.type = type;
        return this;
    }


    public List<CustomerWithType> execute(EntityManager entityManager) {

        DbColumn customerTypeColumn = customerTable.addColumn("TYPE_ID");
        DbColumn lastNameColumn = customerTable.addColumn("lastName");

        DbColumn typeIdColumn = customerType.addColumn("ID");

        SelectQuery q = new SelectQuery()
                .addAllColumns()
                .addFromTable(customerTable)
                .addFromTable(customerType)
                .addCondition(BinaryCondition.equalTo(customerTypeColumn, typeIdColumn))
                .validate();

        // We can add condition with column just as text
        Optional.ofNullable(firstName).map(t -> q.addCondition(new CustomCondition("c.firstName = '" + t + "'")));

        // Wer can add condition using column object
        Optional.ofNullable(lastName).map(t -> q.addCondition(BinaryCondition.equalTo(lastNameColumn, t)));

        Optional.ofNullable(type).map(t -> q.addCondition(new InCondition(customerTypeColumn, t)));

        LOG.info("Incoming parameters: firstName [{}] lastName [{}] type [{}", firstName, lastName, type);
        LOG.info("Sql request [{}]", q);

        return entityManager.createNativeQuery(q.toString(), "customerWithTypeMapping").getResultList();
    }

}
