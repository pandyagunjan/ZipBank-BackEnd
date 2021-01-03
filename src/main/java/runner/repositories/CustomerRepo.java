package runner.repositories;

import runner.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import runner.entities.Login;

@Repository
public interface CustomerRepo<delete> extends CrudRepository<Customer,Long> {

    Customer findCustomerById(Long Id);
    Customer findCustomerByLoginUsername(String name);
    // List<User> findAll();
    //update  --save
    //create -- save
    // delete --delete
}
