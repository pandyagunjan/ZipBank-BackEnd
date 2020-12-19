package runner.repositories;

import runner.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import runner.entities.User;

import java.util.List;

@Repository
public interface AccountRepo extends CrudRepository<Account,Long> {

    Account findAccountById(Long Id);
}
