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
import runner.entities.*;
import runner.enums.AccountType;
import runner.repositories.CustomerRepo;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRunner.class)
@TestPropertySource(properties = {"DB_USER=newuser",
        "DB_PASS=password",
        "DB_URL=jdbc:mysql://localhost:3306/moneymanagement"})
public class CustomerServiceTest {
    @Mock
    CustomerRepo customerRepo;
    @InjectMocks
    //@MockBean
    private CustomerServices customerServices; //Calls the mockito

    Account account1;
    Account account2;
    Account account3;
    Set<Account> testAccounts;
    Login login;
    Login login1;
    Customer customer;
    Address address;
    List<String> logins ;

    @Before
    public void setup(){
        account1 = new Account(1L,"12345", AccountType.CHECKING,100.00,"abcdefg", new HashSet<Transaction>());
        account2 = new Account(2L,"54321", AccountType.SAVINGS,0.00,"gfedcba", new HashSet<Transaction>());
        account3 =  new Account(2L,"56789", AccountType.SAVINGS,100.00,"qwerty", new HashSet<Transaction>());
        testAccounts = new HashSet<Account>();
        testAccounts.add(account1);
        testAccounts.add(account2);
        login = new Login(1L,"user","password",customer); //customer would be null here due to order of code;
        login1 = new Login(2L,"user","password",customer); //customer would be null here due to order of code;
        address = new Address(1L,"Address Line 1", "Address Line 2", "Bear","DE","19701");
        customer = new Customer(1L,"John","Doe",address,login,testAccounts);
    }

    @Test
    public void readUserByIdTest() {
        String expectedName= "John";
        Mockito.when(customerRepo.findCustomerById(1L)).thenReturn(customer);
        String testName = customerServices.readCustomer(1L).getFirstName();
        Assert.assertEquals(expectedName, testName);
    }

    @Test
    public void readUserByLoginTest() {
        String expectedName= "John";
        Mockito.when(customerRepo.findCustomerByLoginUsername("user")).thenReturn(customer);
        String testName = customerServices.readCustomerByLogin("user").getFirstName();
        Assert.assertEquals(expectedName, testName);
    }
    @Test
    public void createUserTest() {

//        String expectedName= "John";
//        Mockito.when(customerRepo.save(customer)).thenReturn(customer);
////      Mockito.when(customerRepo.findCustomerById(customer.getId())).thenReturn(customer);
//        Customer actual=   customerServices.createCustomer(customer);
//        String actualName = actual.getFirstName();
//        Assert.assertEquals(expectedName, actualName);

        Customer expectedCustomer = customer;
        Mockito.when(customerRepo.findCustomerById(any())).thenReturn(null);
        Mockito.when(customerRepo.save(any())).thenReturn(expectedCustomer);
        Customer actualCustomer = customerServices.createCustomer(customer);
        Assert.assertTrue(expectedCustomer.getId()!=0);


    }
    @Test
    public void deleteUserTest() {
        int expected = 2;
        Mockito.when(customerRepo.save(customer)).thenReturn(customer);
        Mockito.when(customerRepo.findCustomerById(customer.getId())).thenReturn(customer);
        int actual= customerServices.deleteCustomer(customer.getId());
         Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkAccountBalanceAndDeleteTest() {
        int expected = 2;
        Mockito.when(customerRepo.save(customer)).thenReturn(customer);
        Mockito.when(customerRepo.findCustomerById(customer.getId())).thenReturn(customer);
        int actual= customerServices.checkAccountBalanceAndDelete(customer.getId(),customer);
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void updateUserTest() throws Exception {
        Customer customer1 = new Customer( 2L, "Radha" , "Ramnik",address,login,testAccounts);
        String expectedUpdateName= "Update the First Name";
        Mockito.when(customerRepo.save(customer1)).thenReturn(customer1);
        Mockito.when(customerRepo.findCustomerById(customer1.getId())).thenReturn(customer1);
        customer1.setFirstName(expectedUpdateName);
        String actualUpdatedName= customerServices.updateCustomer(customer1.getId(), customer1).getFirstName();
        Assert.assertEquals(expectedUpdateName, actualUpdatedName);
    }
    @Test
    public void updateUserPhoneNumberTest() throws Exception {
        Customer customer1 = new Customer( 2L, "Radha" , "Ramnik",address,login,testAccounts);
        int expected = 0;
        Mockito.when(customerRepo.save(customer1)).thenReturn(customer1);
        Mockito.when(customerRepo.findCustomerById(customer1.getId())).thenReturn(customer1);
        String phoneNumber = "514-454-8974";
        int actual= customerServices.updateCustomerPhoneNumber(customer1.getId(), phoneNumber);
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void updateUserEmailTest() throws Exception {
        Customer customer1 = new Customer( 2L, "Radha" , "Ramnik",address,login,testAccounts);
        int expected = 0;
        Mockito.when(customerRepo.save(customer1)).thenReturn(customer1);
        Mockito.when(customerRepo.findCustomerById(customer1.getId())).thenReturn(customer1);
        String email = "gtest@gmail.com";
        int actual= customerServices.updateCustomerEmail(customer1.getId(), email);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateUserAddressTest() throws Exception {
        Customer customer1 = new Customer( 2L, "Radha" , "Ramnik",address,login,testAccounts);
        int expected = 0;
        Mockito.when(customerRepo.save(customer1)).thenReturn(customer1);
        Mockito.when(customerRepo.findCustomerById(customer1.getId())).thenReturn(customer1);
        address.setFirstLine("First Line has been changed");
        Customer actualCustomer= customerServices.updateCustomerAddress(customer1.getId(), address);
        Assert.assertEquals(address.getFirstLine(), actualCustomer.getAddress().getFirstLine());
    }
    @Test
    public void checkLoginTest() throws Exception {
        Boolean expected =true;
        Mockito.when(customerRepo.save(customer)).thenReturn(customer);
        Mockito.when(customerRepo.findAllLoginsNative()).thenReturn(logins);
        Boolean actual= customerServices.checkLogin(login);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllAccountsTest() throws Exception {
        Mockito.when(customerRepo.findCustomerById(customer.getId())).thenReturn(customer);
        Set<Account> actualAccounts= customerServices.getAllAccounts(customer.getId());
        Assert.assertEquals(testAccounts.size(), actualAccounts.size());
    }

    @Test
    public void generateRandomURLTest() throws Exception {
        String actual = customerServices.generateRandomUrl();
        Assert.assertTrue(actual.length()!=0);
    }
}