package runner.services;
import runner.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runner.entities.User;
import runner.repositories.AccountRepo;
import java.util.Optional;
@Service
public class AccountServices {
    @Autowired
    private AccountRepo accountRepo;
    //CRUD methods
    public Account createAccount(Account account)
    {
        //Re-direct to POST in ACCOUNT controller
        return accountRepo.save(account);
    }
    public Account readAccount(Long id)
    {
        //Re-direct to GET in ACCOUNT controller
        return accountRepo.findAccountById(id);
    }
    public Boolean removeAccount(Long id)
    {
        Account accountFromDB = accountRepo.findAccountById(id);
        accountRepo.delete(accountFromDB);
        return accountRepo.existsById(id);
    }
    public Optional<Account> updateAccount(Long id , Account account)
    {
        Account accountFromDB = accountRepo.findAccountById(id);
        accountFromDB.setAccountType(account.getAccountType());
        accountFromDB.setAccountNumber(account.getAccountNumber());
        accountFromDB.setInterestRate(account.getInterestRate());
        accountFromDB.setDateOfOpening(account.getDateOfOpening());
        accountFromDB.setRoutingNumber(account.getRoutingNumber());
        accountFromDB.setBalance(account.getBalance());
        accountFromDB.setUser(account.getUser());
        return Optional.of(accountFromDB);
    }
    public Double withdraw(Double amount, Long Id)
    {
        //Login to withdraw from the account
        return 0d;
    }
    public Double deposit(Double amount , Long Id)
    {
        //Login to withdraw from the account
        return 0d;
    }
    public Double transfer(Double amount , Long fromId ,Long toId)
    {
        //Login to withdraw from the account
        return 0d;
    }
}