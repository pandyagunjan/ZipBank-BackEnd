package runner.repositories;

import runner.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepo extends CrudRepository<Customer,Long> {

    Customer findCustomerById(Long Id);
    Customer findCustomerByLoginUsername(String name);

}