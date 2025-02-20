package lv.nixx.poc.repository.advanced;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lv.nixx.poc.orm.Customer;
import lv.nixx.poc.repository.useraware.CustomUserAwareRepositoryImpl;
import lv.nixx.poc.service.UserLoginProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public class CustomerCustomOperationsImpl extends CustomUserAwareRepositoryImpl<Customer> implements CustomerCustomOperations {

    public CustomerCustomOperationsImpl(EntityManager entityManager, UserLoginProvider userLoginProvider) {
        super(entityManager, userLoginProvider);
    }

    @Override
    public Collection<Customer> findCustomersUsingCustomCondition(String name, String type) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
        Root<Customer> customer = query.from(Customer.class);

        Collection<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(name)) {
            predicates.add(cb.like(customer.get("name"), "%" + name + "%"));
        }

        if (StringUtils.isNotBlank(type)) {
            predicates.add(cb.equal(customer.get("type"), type));
        }

        if (!predicates.isEmpty()) {
            query.select(customer).where(cb.and(predicates.toArray(new Predicate[0])));
        } else {
            query.select(customer);
        }

        return entityManager.createQuery(query).getResultList();
    }

}
