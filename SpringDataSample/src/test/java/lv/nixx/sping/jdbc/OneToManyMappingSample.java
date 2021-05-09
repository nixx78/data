package lv.nixx.sping.jdbc;

import lv.nixx.poc.spring.data.MainDBConfig;
import lv.nixx.poc.spring.data.domain.dto.AddressDTO;
import lv.nixx.poc.spring.data.domain.dto.CustomerDTO;
import lv.nixx.poc.spring.data.domain.main.Adress;
import lv.nixx.poc.spring.data.domain.main.Customer;
import lv.nixx.poc.spring.data.domain.main.CustomerType;
import lv.nixx.poc.spring.data.repository.main.AdressRepository;
import lv.nixx.poc.spring.data.repository.main.CustomerDAO;
import lv.nixx.poc.spring.data.repository.main.CustomerRepository;
import lv.nixx.poc.spring.data.repository.main.TypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainDBConfig.class)
public class OneToManyMappingSample {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private AdressRepository adressRepository;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;


    @Before
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);

        customerRepository.deleteAll();
        adressRepository.deleteAll();
        typeRepository.deleteAll();

        final CustomerType simpleCustomer = new CustomerType("simple", "Simple Customer");
        typeRepository.save(simpleCustomer);

        Customer c1 = new Customer("FirstName.1", "LastName.1", simpleCustomer);
        c1.addAdress(new Adress("N1 line1", "N1 line2"));
        c1.addAdress(new Adress("N2 line1", "N2 line2"));

        customerDAO.save(c1);

        Customer c2 = new Customer("FirstName.2", "LastName.2", simpleCustomer);
        c2.addAdress(new Adress("N1 line1", "N1 line2"));
        c2.addAdress(new Adress("N2 line1", "N2 line2"));

        customerDAO.save(c2);

        customerDAO.save(new Customer("FirstName.3", "LastName.3", simpleCustomer));
    }

    @Test
    public void manualMappingSample() {

        Collection<CustomerDTO> res = new HashSet<>(jdbcTemplate.query(
                "SELECT c.*, a.customer_id as cid, a.id as aid, a.line1, a.line2 FROM Customer c LEFT JOIN Adress a on c.id = a.customer_id",
                new ManualOneToManyMapper()));

        System.out.println(res);
    }

    static class ManualOneToManyMapper implements RowMapper<CustomerDTO> {

        Map<Long, CustomerDTO> m = new HashMap<>();

        @Override
        public CustomerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

            long id = rs.getLong("id");
            CustomerDTO customerDTO = m.get(id);
            if (customerDTO == null) {
                customerDTO = new CustomerDTO()
                        .setId(id)
                        .setName(rs.getString("firstName") + " " + rs.getString("lastName"));
            }

            AddressDTO a = new AddressDTO()
                    .setId(rs.getLong("aid"))
                    .setLine1(rs.getString("line1"))
                    .setLine2(rs.getString("line2"));

            if (a.notEmpty()) {
                customerDTO.addAddress(a);
            }

            return customerDTO;
        }
    }

}
