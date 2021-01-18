package runner.services;
import com.mifmif.common.regex.Generex;
import runner.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runner.entities.Transaction;
import runner.repositories.AccountRepo;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public void saveAccount(Account account){
        accountRepo.save(account);
    }

    public void SaveAccountWithUrl(Account account, String randomUrl){
            account.setEncryptedUrl(randomUrl);
            accountRepo.save(account);
    }

    public Account createAccount(Account account, String username) throws Exception {
        loggerService.log(Level.INFO, "The customer's new account is being saved and given an account number.");
        account.setCustomer(accountRepo.findAccountsByCustomer_LoginUsername(username).stream().findFirst().orElse(null).getCustomer());
        setUpAccount(account);
        transferMoneyToNewAccount(account);
        account.setEncryptedUrl(generateRandomUrl());
        return accountRepo.save(account);
    }

    public void transferMoneyToNewAccount(Account newAccount) throws Exception {
        Transaction customerTransaction = newAccount.getTransactions().stream().findFirst().orElse(null);
        Account sourceAccount = getAccountByAccountNumber(
                customerTransaction.getAccounts().stream().findFirst().orElse(null).getAccountNumber());
        transferMoney(customerTransaction,sourceAccount,newAccount);
        //removes the empty transaction owned by the new account due to structure of the JSON, newaccount->transactions->account
        newAccount.getTransactions().remove(newAccount.getTransactions().stream()
                .filter(transaction -> transaction.getTransactionBalance()==null).findFirst().orElse(null));
    }

    public Account setUpAccount(Account account) {
        Boolean created = false;
        while (!created) {
            //double temp = Math.floor(Math.random() * 1000000000); <--this only makes a 9 digit number
            long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
            if (accountRepo.findAccountByAccountNumber(String.valueOf(number)) == null) {
                account.setAccountNumber(String.valueOf(number));
                account.setDateOfOpening(LocalDate.now());
                account.setBalance(0.00);
                account.setInterestRate(account.getInterestRate());
                created = true;
            }
        }
        return account;
    }

    public Account getAccountByEncryptedUrl(String encryptedUrl){
        Account individualAccount = accountRepo.findAccountByEncryptedUrl(encryptedUrl);
        List<Transaction> sortedList = accountRepo.findAccountByEncryptedUrl(encryptedUrl).getTransactions().stream()
                .sorted(Comparator.comparingLong(Transaction::getId).reversed()).collect(Collectors.toList());
        individualAccount.setTransactions(sortedList);
        return individualAccount;
    }

    public Account getAccountByAccountNumber(String accountNumber){
        return accountRepo.findAccountByAccountNumber(accountNumber);
    }

    //Remove if not needed
/*    public Account readAccount(Long id) throws Exception{
        loggerService.log(Level.INFO, "Attempting to read customer's account # " + id);
        if (accountRepo.existsById(id) == true) {
            loggerService.log(Level.INFO, "The customer's account #" + id + "is being read'");
            return accountRepo.findAccountById(id);
        }
        loggerService.log(Level.WARNING, "The customer is trying to read account # " + id + "that doe not exist");
        throw new Exception("Account does not exist");
    }*/

    //Remove if not needed
/*    public Boolean removeAccount(Long id) throws Exception{
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
    }*/

    public Boolean removeAccount(String encryptedUrl){
        if(accountRepo.findAccountByEncryptedUrl(encryptedUrl).getBalance()==0) {
            accountRepo.deleteAccountByEncryptedUrl(encryptedUrl);
            return true;
        }
        return false;
    }

    //REMOVE if not used
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
        List<Transaction> fromSet = fromAccount.getTransactions();
        List<Transaction> toSet = toAccount.getTransactions();
        fromSet.add(transactionsList.get(0));
        toSet.add(transactionsList.get(1));
        fromAccount.setTransactions(fromSet);
        toAccount.setTransactions(toSet);

        Account[] accountArray = {fromAccount,toAccount};
        return accountArray;
    }

    //need to add method inside here to check if the routing and account numbers are valid
    public Account deposit(Transaction transaction, String encryptedUrl) throws Exception{
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

    public String logout(String username){
        this.getAllAccounts(username).stream().forEach(a->a.setEncryptedUrl(null));
        this.getAllAccounts(username).stream().forEach(a->this.saveAccount(a));
        return "You have been logged out";
    }

    //generate 35-40 random characters
    public String generateRandomUrl() {
        Generex generex = new Generex("[A-Za-z0-9]{35,40}");
        String randomString = generex.random();
        return randomString;
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