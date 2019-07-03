package lv.nixx.poc.db.request;

import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;

import javax.persistence.EntityManager;
import java.util.Collection;

public abstract class GenericCustomerRequest<T> {

    private final DbSchema schema;

    final DbSpec spec;
    final DbColumn lastNameCustomerCol;
    final DbColumn firstNameCustomerCol;

    DbTable customerTable;

    public abstract Collection<T> execute(EntityManager em);

    DbTable addTableToSpec(String tableName, String alias) {
        return new DbTable(schema, tableName, alias);
    }

    public GenericCustomerRequest() {
        this.spec = new DbSpec();
        this.schema = spec.addSchema("app");

        customerTable = new DbTable(schema, "Customer", "c");

        this.lastNameCustomerCol = customerTable.addColumn("lastName");
        this.firstNameCustomerCol = customerTable.addColumn("firstName");

    }

}
