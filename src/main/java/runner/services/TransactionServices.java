package runner.services;

import runner.entities.Account;
import runner.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runner.repositories.TransactionRepo;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

@Service
public class TransactionServices {
    @Autowired
    private TransactionRepo transactionRepo;
    //CRUD methods
    public Optional<Transaction> createTransaction()
    {
        //Used in POST for Transaction controller
        return null;
    }

    public Optional<Transaction> readTransaction()
    {
        //Used in GET in Transaction controller
        return null;
    }
    public Boolean removeTransaction()
    {
        //Used in DELETE in Transaction controller
        return true;
    }

    public Optional<Transaction> updateTransaction()
    {
        //Used in PUT in Transaction controller
        return null;
    }

    public ArrayList<Transaction> setAllTransactions(Transaction transaction, Account fromAccount, Account toAccount){
        //{0 = withdraw, 1 = deposit} money always leaves from 'fromAccount' to 'toAccount'
        ArrayList<Transaction> myList = new ArrayList<Transaction>();

        myList.add(setOneTransaction(transaction, fromAccount, toAccount, true));
        myList.add(setOneTransaction(transaction, toAccount, fromAccount, false));
        return myList;
    }

    public Transaction setOneTransaction(Transaction transaction, Account account1, Account account2, Boolean withdrawType) {
        Transaction newTransaction = new Transaction();
        String AccountNum = account2.getAccountNumber(); //get the account number for formatting description
        if(withdrawType) {
            newTransaction.setTransactionDescription(String.format("Withdrawal to %s XXXXXXX%s", account2.getAccountType(),
                    AccountNum.substring(AccountNum.length() - 4))); //format description with last 4 digits of account
            newTransaction.setTransactionAmount(transaction.getTransactionAmount()*(-1)); //making "to" transaction amount negative
        }
        else{
            newTransaction.setTransactionDescription(String.format("Deposit from %s XXXXXXX%s", account2.getAccountType(),
                    AccountNum.substring(AccountNum.length() - 4))); //format description with last 4 digits of account
            newTransaction.setTransactionAmount(transaction.getTransactionAmount()); //making "to" transaction amount negative
        }

        newTransaction.setTransactionBalance(account1.getBalance()); //set the new balance from account
        newTransaction.setTransactionDate(LocalDate.now()); //set today's date

        return newTransaction;
    }

}
