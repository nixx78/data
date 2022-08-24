package lv.nixx.poc.db.jpa;


import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//TODO https://www.baeldung.com/jpa-hibernate-batch-insert-update
public class CustomerBatchUpdateTest {

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("test.unit");

    @Test
    public void batchUpdateTest() {



    }

    /*
    private static class ProxyDataSourceInterceptor implements MethodInterceptor {
        private final DataSource dataSource;
        public ProxyDataSourceInterceptor(final DataSource dataSource) {
            this.dataSource = ProxyDataSourceBuilder.create(dataSource)
                    .name("Batch-Insert-Logger")
                    .asJson().countQuery().logQueryToSysOut().build();
        }
    }
     */

}
