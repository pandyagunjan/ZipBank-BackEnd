package runner.services;
import runner.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runner.enums.AccountType;
import runner.repositories.AccountRepo;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AccountServices {
    private final static Logger loggerService = Logger.getLogger(AccountServices.class.getName());

    @Autowired
    private AccountRepo accountRepo;

    public Account createAccount(Account account) {
        loggerService.log(Level.INFO, "The customer's new account is being saved");
        return accountRepo.save(account);
    }

    public Account readAccount(Long id) throws Exception{
        loggerService.log(Level.INFO, "Attempting to read customer's account # " + id);
        if (accountRepo.existsById(id) == true) {
            loggerService.log(Level.INFO, "The customer's account #" + id + "is being read'");
            return accountRepo.findAccountById(id);
        }
        loggerService.log(Level.WARNING, "The customer is trying to read account # " + id + "that doe not exist");
        throw new Exception("Account does not exist");
    }

    public Boolean removeAccount(Long id) throws Exception{
        loggerService.log(Level.INFO, "Attempting to remove customer's account # " + id);
        if (accountRepo.findAccountById(id).getBalance() == 0) {
            loggerService.log(Level.INFO, "The customer is removing the account # " + id);
            Account accountFromDB = accountRepo.findAccountById(id);
            accountRepo.delete(accountFromDB);
            return accountRepo.existsById(id);
        } else {

            loggerService.log(Level.WARNING, "The customer had a balance greater than 0 and could not remove the account # " + id);
            throw new Exception("Balance not 0 cannot be closed");
        }
    }

    public Optional<Account> updateAccount(Long id, Account account) throws Exception{
        loggerService.log(Level.INFO, "Attempting to update customer's account # " + id);
        if (accountRepo.existsById(id) == true) {
            loggerService.log(Level.INFO, "The customer is updating their account # " + id);
            Account accountFromDB = accountRepo.findAccountById(id);
            accountFromDB.setAccountType(account.getAccountType());
            accountFromDB.setAccountNumber(account.getAccountNumber());
            accountFromDB.setInterestRate(account.getInterestRate());
            accountFromDB.setDateOfOpening(account.getDateOfOpening());
            accountFromDB.setRoutingNumber(account.getRoutingNumber());
            accountFromDB.setBalance(account.getBalance());
            return Optional.of(accountFromDB);
        }
        loggerService.log(Level.WARNING, "The account # " + id + "does not exist to be updated");
        throw new Exception("Account does not exist");
    }

    public Account withdraw(Double amount, Long id) throws Exception{
        loggerService.log(Level.INFO, "The customer is attempting to withdraw " + amount + "from account # " + id);
        if (accountRepo.findAccountById(id).getBalance() > amount) {
            loggerService.log(Level.INFO, "The customer is making a withdraw");
            accountRepo.findAccountById(id).setBalance(accountRepo.findAccountById(id).getBalance() - amount);
            return accountRepo.save(readAccount(id));
        } else {
            loggerService.log(Level.WARNING, "The customer did not have sufficient funds to make the withdraw");
            throw new Exception("Insufficient funds");
        }
    }

    public Account deposit(Double amount, Long id) throws Exception {
        loggerService.log(Level.INFO, "The customer is making a deposit");
        accountRepo.findAccountById(id).setBalance(accountRepo.findAccountById(id).getBalance() + amount);
        return accountRepo.save(readAccount(id));
    }

    public Account transfer(Double amount, Long fromId, Long toId) throws Exception {
        if (accountRepo.findAccountById(fromId).getBalance() > amount) {
            loggerService.log(Level.INFO, "The customer is making a transfer");
            accountRepo.findAccountById(fromId).setBalance(accountRepo.findAccountById(fromId).getBalance() - amount);
            accountRepo.findAccountById(toId).setBalance(accountRepo.findAccountById(toId).getBalance() + amount);
            accountRepo.save(readAccount(toId));
            return accountRepo.save(readAccount(fromId));
        } else {
            loggerService.log(Level.WARNING, "The customer did not have sufficient funds to make the transfer");
            throw new Exception("Insufficient funds");
        }
    }
}