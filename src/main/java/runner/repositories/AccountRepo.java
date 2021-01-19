package runner.repositories;

import org.springframework.data.jpa.repository.Query;
import runner.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface AccountRepo extends CrudRepository<Account,Long> {

    Account findAccountById(Long Id);
    Account findAccountByEncryptedUrl(String encryptedUrl);
    Account findAccountByAccountNumber(String accountNumber);
    Set<Account> findAccountsByCustomer_LoginUsername (String login);
    Account deleteAccountByEncryptedUrl(String encryptedUrl);
}
