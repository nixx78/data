package lv.nixx.poc.repository.projection;

import lv.nixx.poc.orm.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
Использование представлений (Projection) позволяет избежать использования DTO.
В данном примере, из базы возвращаются только 2 поля, одно из которых
является составным. Данных подход, позволяет один раз определить Entity object
со всеми зависимостями, а потом использовать только представления с минимально
необходимым количеством полей.

Важно отметить, что все операции будут выполнятся на стороне базы данных,
что позволит избежать не нужно передачи данных на сторону клиента
 */
@Repository
public interface CustomerProjectionRepository extends JpaRepository<Customer, Long> {

    @Query("Select c.id as id, CONCAT(c.name, ' ', c.surname) AS nameSurname from Customer c order by c.id")
    List<CustomerProjection> findAllCustomersAsProjection();

}
