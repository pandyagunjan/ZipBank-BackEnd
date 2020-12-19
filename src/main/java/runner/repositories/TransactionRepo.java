package runner.repositories;

import runner.entities.TransactionHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends CrudRepository<TransactionHistory , Long> {


}
