package runner.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import runner.AppRunner;
import runner.entities.Account;
import runner.entities.Customer;
import runner.entities.Login;
import runner.entities.Transaction;
import runner.enums.AccountType;
import runner.repositories.AccountRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRunner.class)
@TestPropertySource(properties = {
        "DB_USER=newuser",
        "DB_PASS=password",
        "DB_URL=jdbc:mysql://localhost:3306/moneymanagement"
})
public class TransactionServicesTest {
    @Autowired
            TransactionServices transactionServices;
    Account account1;
    Account account2;
    Transaction transaction;
    Set<Account> transactionAccount;

    @Before
    public void setup(){
        transactionAccount = new HashSet<Account>();
        transactionAccount.add(account1);
        transaction = new Transaction(1.00,transactionAccount);
        account1 = new Account(1L,"12345", AccountType.CHECKING,100.00,"abcdefg", new HashSet<Transaction>());
        account2 = new Account(2L,"54321", AccountType.SAVINGS,0.00,"gfedcba", new HashSet<Transaction>());
    }

    @Test
    public void setAllTransactionTest(){
        ArrayList<Transaction> actualTransactionList = transactionServices.setAllTransactions(transaction,account1,account2);
        Assert.assertTrue(actualTransactionList.size() == 2);
    }

    @Test
    public void setOneTransactionTestWithdraw(){
        Transaction expected = new Transaction(String.format("Withdrawal to %s XXXXXXXX%s",account2.getAccountType(),
                account2.getAccountNumber().substring(account2.getAccountNumber().length()-4)),
                transaction.getTransactionAmount()*(-1), account1.getBalance(), LocalDate.now());

        Transaction actual = transactionServices.setOneTransaction(transaction,account1,account2,true);

        Assert.assertEquals(expected.getTransactionAmount(),actual.getTransactionAmount());
        Assert.assertEquals(expected.getTransactionBalance(),actual.getTransactionBalance());
        Assert.assertEquals(expected.getTransactionDate(),actual.getTransactionDate());
        Assert.assertEquals(expected.getTransactionDescription(),actual.getTransactionDescription());
    }

    @Test
    public void setOneTransactionTestDeposit(){
        Transaction expected = new Transaction(String.format("Deposit from %s XXXXXXXX%s",account1.getAccountType(),
                account1.getAccountNumber().substring(account1.getAccountNumber().length()-4)),
                transaction.getTransactionAmount(), account2.getBalance(), LocalDate.now());

        Transaction actual = transactionServices.setOneTransaction(transaction,account2,account1,false);

        Assert.assertEquals(expected.getTransactionAmount(),actual.getTransactionAmount());
        Assert.assertEquals(expected.getTransactionBalance(),actual.getTransactionBalance());
        Assert.assertEquals(expected.getTransactionDate(),actual.getTransactionDate());
        Assert.assertEquals(expected.getTransactionDescription(),actual.getTransactionDescription());
    }

}
