package runner.repositories;

import runner.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepo extends CrudRepository<Account,Long> {

    Account findAccountById(Long Id);
    Account findAccountByEncryptedUrl(String encryptedUrl);
    Account findAccountByAccountNumber(String accountNumber);

}
