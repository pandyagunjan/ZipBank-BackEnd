package runner.services;
import runner.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runner.entities.Transaction;
import runner.repositories.AccountRepo;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AccountServices {
    private final static Logger loggerService = Logger.getLogger(AccountServices.class.getName());

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private TransactionServices transactionServices;

    public Set<Account> getAllAccounts(String username){
        return accountRepo.findAccountsByCustomer_LoginUsername(username);
    }

    public Account createAccount(Account account) {
        loggerService.log(Level.INFO, "The customer's new account is being saved");
        return accountRepo.save(account);
    }

    public Account findAccountByEncryptedUrl(String encryptedUrl){
        return accountRepo.findAccountByEncryptedUrl(encryptedUrl);
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

    public Boolean removeAccount(String encryptedUrl){
        if(accountRepo.findAccountByEncryptedUrl(encryptedUrl).getBalance()==0) {
            accountRepo.deleteAccountByEncryptedUrl(encryptedUrl);
            return true;
        }
        return false;
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

    //iterate through set to get accounts but should only be one at any time
    public Account iteratorReturn(Iterator<Account> iterator){
        while(iterator.hasNext()){
            return iterator.next();
        }
        return null;
    }

    public Account[] transferMoney(Transaction transaction, Account fromAccount, Account toAccount) throws Exception{
        Double amount = transaction.getTransactionAmount();

        //check balance;
        if(amount>fromAccount.getBalance()){
            throw new Exception("Insufficient funds");
        }

        //do the math, all that stuff below is to make sure the number stays 2 decimal places
        fromAccount.setBalance(Math.round((fromAccount.getBalance() - amount)*100.0)/100.0);
        toAccount.setBalance(Math.round((toAccount.getBalance() + amount)*100.0)/100.0);

        //adding new transactions to account's transactions set
        ArrayList<Transaction> transactionsList = transactionServices.setAllTransactions(transaction, fromAccount, toAccount);
        Set<Transaction> fromSet = fromAccount.getTransactions();
        Set<Transaction> toSet = toAccount.getTransactions();
        fromSet.add(transactionsList.get(0));
        toSet.add(transactionsList.get(1));
        fromAccount.setTransactions(fromSet);
        toAccount.setTransactions(toSet);

        Account[] accountArray = {fromAccount,toAccount};
        return accountArray;
    }

    //need to add method inside here to check if the routing and account numbers are valid
    public Account deposit(Transaction transaction, String encryptedUrl) throws Exception{ //zekai
        loggerService.log(Level.INFO, "The customer is making a deposit");
        Account toAccount = accountRepo.findAccountByEncryptedUrl(encryptedUrl);

        Iterator<Account> iterator = transaction.getAccounts().iterator();
        String accountNum = iteratorReturn(iterator).getAccountNumber();
        Account fromAccount = accountRepo.findAccountByAccountNumber(accountNum);

        Account[] myAccountArray = transferMoney(transaction,fromAccount,toAccount);

        accountRepo.save(myAccountArray[0]); //1st element in list/array always account money is moving out of
        return accountRepo.save(myAccountArray[1]); //2nd element in list/array always account money is moving into
    }

    //need to add method inside here to check if the routing and account numbers are valid
    public Account withdraw(Transaction transaction, String encryptedUrl) throws Exception{
        loggerService.log(Level.INFO, "The customer is making a withdrawal");
        Account fromAccount = accountRepo.findAccountByEncryptedUrl(encryptedUrl);

        Iterator<Account> iterator = transaction.getAccounts().iterator();
        String accountNum = iteratorReturn(iterator).getAccountNumber();
        Account toAccount = accountRepo.findAccountByAccountNumber(accountNum);

        Account[] myAccountArray = transferMoney(transaction,fromAccount,toAccount);

        accountRepo.save(myAccountArray[1]); //1st element in list/array always account money is moving out of
        return accountRepo.save(myAccountArray[0]); //2nd element in list/array always account money is moving into
    }

    // Not needed, transfer and withdraw have same JSON payload; so use withdraw method
/*    public Account transfer(Double amount, Long fromId, Long toId) throws Exception {
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
    }*/

}