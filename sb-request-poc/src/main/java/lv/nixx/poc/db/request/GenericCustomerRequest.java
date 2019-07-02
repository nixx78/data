package lv.nixx.poc.db.request;

import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;

import javax.persistence.EntityManager;
import java.util.Collection;

public abstract class GenericCustomerRequest<T> {

    final DbSpec spec;
    private final DbSchema schema;

    DbTable customerTable;

    public abstract Collection<T> execute(EntityManager em);

    DbTable addTableToSpec(String tableName, String alias) {
        return new DbTable(schema, tableName, alias);
    }

    public GenericCustomerRequest() {
        this.spec = new DbSpec();
        this.schema = spec.addSchema("app");
        customerTable = new DbTable(schema, "Customer", "c");
    }

}
