package runner.repositories;

import org.springframework.data.jpa.repository.Query;
import runner.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CustomerRepo extends CrudRepository<Customer,Long> {

    Customer findCustomerById(Long Id);
    Customer findCustomerByLoginUsername(String name);

    //Below query was not used and hence the streams operations were modified
    @Query(
            value = "SELECT username FROM LOGIN",
            nativeQuery = true)

    List<String> findAllLoginsNative();
    List<Customer> findAll();
}