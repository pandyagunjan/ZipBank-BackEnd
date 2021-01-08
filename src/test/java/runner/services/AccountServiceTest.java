package runner.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
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

import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRunner.class)
@TestPropertySource(properties = {
        "DB_USER=newuser",
        "DB_PASS=password",
        "DB_URL=jdbc:mysql://localhost:3306/moneymanagement"
})

public class AccountServiceTest {
    @Mock
    AccountRepo accountRepo;
    @Mock
    TransactionServices transactionServices;
    @InjectMocks
    AccountServices accountServices;
    Account account1;
    Account account2;
    Account account3;
    Set<Account> testAccounts;
    Login login;
    Customer customer;
    Transaction transaction;
    Set<Account> transactionAccount;
    Transaction withdrawalTransaction;
    Transaction depositTransaction;
    ArrayList<Transaction> myTransactionList;

    @Before
    public void setup(){
        account1 = new Account(1L,"12345", AccountType.CHECKING,100.00,"abcdefg", new HashSet<Transaction>());
        account2 = new Account(2L,"54321", AccountType.SAVINGS,0.00,"gfedcba", new HashSet<Transaction>());
        account3 =  new Account(2L,"56789", AccountType.SAVINGS,100.00,"qwerty", new HashSet<Transaction>());
        testAccounts = new HashSet<Account>();
        testAccounts.add(account1);
        testAccounts.add(account2);
        login = new Login(1L,"user","password",customer); //customer would be null here due to order of code;
        customer = new Customer(1L,"John","Doe",login,testAccounts);
        transactionAccount = new HashSet<Account>();
        transactionAccount.add(account1);
        transaction = new Transaction(1.00,transactionAccount);

        //=============== setup for transfer tests below, also used in deposit and withdraw ====================

        withdrawalTransaction = new Transaction(String.format("Withdrawal to %s XXXXXXXX%s",account2.getAccountType(),
                account2.getAccountNumber().substring(account2.getAccountNumber().length()-4)),
                transaction.getTransactionAmount()*(-1), account1.getBalance(), LocalDate.now());

        depositTransaction = new Transaction(String.format("Deposit from %s XXXXXXXX%s",account1.getAccountType(),
                account1.getAccountNumber().substring(account1.getAccountNumber().length()-4)),
                transaction.getTransactionAmount()*(-1), account2.getBalance(), LocalDate.now());

        myTransactionList = new ArrayList<Transaction>();
        myTransactionList.add(withdrawalTransaction);
        myTransactionList.add(depositTransaction);
    }

    //PREFACE: we know the behavior of the accountRepo, this is unit test for accountServices; therefore, the results are set by the tester fro the repo

    @Test
    public void getAllAccountsTest(){
        //given: when accountServices.getAlAccounts calls accountRepo.findAccountsByCustomer_LoginUsername, it will return the test Accounts;
        Set<Account> expectedAccounts = testAccounts;
        Mockito.when(accountRepo.findAccountsByCustomer_LoginUsername(any())).thenReturn(testAccounts);
        //when: telling accountServices to get all accounts
        Set<Account> actualAccounts = accountServices.getAllAccounts(login.getUsername());
        //the two sets should be identical
        Assert.assertTrue(actualAccounts.containsAll(expectedAccounts));
    }

    @Test
    public void findAccountByEncryptedUrlTest(){
        Account expectedAccount = account1;
        Mockito.when(accountRepo.findAccountByEncryptedUrl(any())).thenReturn(account1);

        Account actualAccount = accountServices.getAccountByEncryptedUrl(account1.getEncryptedUrl());

        Assert.assertTrue(expectedAccount.equals(actualAccount));
    }

    @Test
    public void createAccountTest() throws Exception {
        Account expectedAccount = account1;
        Mockito.when(accountRepo.findAccountByAccountNumber(any())).thenReturn(null);
        Mockito.when(accountRepo.save(any())).thenReturn(expectedAccount);

        Account actualAccount = accountServices.createAccount(expectedAccount, login.getUsername());

        Assert.assertTrue(expectedAccount.getAccountNumber().length()==10);
    }

    @Test
    public void removeAccountTestFalse() {
        String encryptedUrl = account1.getEncryptedUrl();
        Mockito.when(accountRepo.deleteAccountByEncryptedUrl(any())).thenReturn(account1);
        Mockito.when(accountRepo.findAccountByEncryptedUrl(any())).thenReturn(account1);

        Boolean deleted = accountServices.removeAccount(encryptedUrl);

        Assert.assertFalse(deleted);
    }

    @Test
    public void removeAccountTestTrue() {
        String encryptedUrl = account2.getEncryptedUrl();
        Mockito.when(accountRepo.deleteAccountByEncryptedUrl(any())).thenReturn(account2);
        Mockito.when(accountRepo.findAccountByEncryptedUrl(any())).thenReturn(account2);

        Boolean deleted = accountServices.removeAccount(encryptedUrl);

        Assert.assertTrue(deleted);
    }

    @Test
    public void transferMoneyTestTrue() throws Exception {
        Account expectedAccount1 = account1;
        Account expectedAccount2 = account2;
        expectedAccount1.setBalance(account1.getBalance()-transaction.getTransactionAmount());
        expectedAccount2.setBalance(account2.getBalance()+transaction.getTransactionAmount());

        Mockito.when(transactionServices.setAllTransactions(any(),any(),any())).thenReturn(myTransactionList);

        Account[] actualAccounts = accountServices.transferMoney(transaction,account1,account2);

        Assert.assertEquals(actualAccounts[0].getBalance(),expectedAccount1.getBalance());
        Assert.assertEquals(actualAccounts[1].getBalance(),expectedAccount2.getBalance());
        Assert.assertTrue(actualAccounts[0].getTransactions().contains(withdrawalTransaction));
        Assert.assertTrue(actualAccounts[1].getTransactions().contains(depositTransaction));
    }

    @Test(expected = Exception.class) //account the money is being withdrawn from has insufficient funds
    public void transferMoneyTestFalse() throws Exception {
        Mockito.when(transactionServices.setAllTransactions(any(),any(),any())).thenReturn(myTransactionList);
        Account[] actualAccounts = accountServices.transferMoney(transaction,account2,account1);
    }


    @Test
    public void withdrawTest() throws Exception {
        String encryptedUrl = account1.getEncryptedUrl();
        Account expectedAccount = account1;
        expectedAccount.setBalance(expectedAccount.getBalance()-transaction.getTransactionAmount());
        Mockito.when(accountRepo.findAccountByEncryptedUrl(any())).thenReturn(account3);
        Mockito.when(accountRepo.findAccountByAccountNumber(any())).thenReturn(account1);
        Mockito.when(transactionServices.setAllTransactions(any(),any(),any())).thenReturn(myTransactionList);
        Mockito.when(accountRepo.save(any())).thenReturn(account3, account1); //first and second instance

        Account actualAccount = accountServices.withdraw(transaction,encryptedUrl);

        Assert.assertEquals(expectedAccount.getBalance(),actualAccount.getBalance());
    }

    @Test
    public void depositTest() throws Exception {
        String encryptedUrl = account1.getEncryptedUrl();
        Account expectedAccount = account1;
        expectedAccount.setBalance(expectedAccount.getBalance()+transaction.getTransactionAmount());
        Mockito.when(accountRepo.findAccountByEncryptedUrl(any())).thenReturn(account3);
        Mockito.when(accountRepo.findAccountByAccountNumber(any())).thenReturn(account1);
        Mockito.when(transactionServices.setAllTransactions(any(),any(),any())).thenReturn(myTransactionList);
        Mockito.when(accountRepo.save(any())).thenReturn(account3, account1); //first and second instance

        Account actualAccount = accountServices.deposit(transaction,encryptedUrl);

        Assert.assertEquals(expectedAccount.getBalance(),actualAccount.getBalance());
    }


    @Test
    public void generateRandomURLTest() throws Exception {
        String actual = accountServices.generateRandomUrl();
        Assert.assertTrue(actual.length()!=0);
    }

}
