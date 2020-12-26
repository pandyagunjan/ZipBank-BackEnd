package runner.userTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import runner.AppRunner;
import runner.entities.*;
import runner.enums.AccountType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRunner.class)
public class UserEntityTest {

    private User user;
    private Login login;
    private Set<Account> expectedAccount;
    private Account account1;
    private Address expectedAddress;


    @Before
    public void startUp()
    {
        user= new UserBuilder().createUser();
    }

    @Test
    public void setAndGetIdTest()
    {
        //Given
        Long expected =1L;
        //When
        user.setId(expected);
        //Then
        Long actual = user.getId();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void setAndGetFirstNameTest()
    {
        //Given
        String expected = "FirstName";
        //When
        user.setFirstName(expected);
        //Then
        String actual = user.getFirstName();
        Assert.assertEquals(expected,actual);
    }


    @Test
    public void setAndGetLastNameTest()
    {
        //Given
        String expected = "LastName";
        //When
        user.setLastName(expected);
        //Then
        String actual = user.getLastName();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void setAndGetMiddleNameTest()
    {
        //Given
        String expected = "MiddleName";
        //When
        user.setMiddleName(expected);
        //Then
        String actual = user.getMiddleName();
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void setAndGetDOBTest()
    {
        //Given
        LocalDate expected = LocalDate.now();
        //When
        user.setDateOfBirth(expected);
        //Then
        LocalDate actual = user.getDateOfBirth();
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void setAndGetSSNTest()
    {
        //Given
        String expected ="234-333-444";
        //When
        user.setSocialSecurity(expected);
        //Then
        String actual = user.getSocialSecurity();
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void setAndGetPhoneNumberTest()
    {
        //Given
        String expected ="234-333-4544";
        //When
        user.setPhoneNumber(expected);
        //Then
        String actual = user.getPhoneNumber();
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void setAndGetEmailTest()
    {
        //Given
        String expected ="test@gmail.com";
        //When
        user.setEmail(expected);
        //Then
        String actual = user.getEmail();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void setAndGetLoginTest()
    {
        //Given
         login = new Login();
         login.setId(1l);;
         login.setPassword("test");
         login.setUsername("user");

        //When
        user.setLogin(login);
        //Then
        Login actual = user.getLogin();
        Assert.assertEquals(login,actual);
    }

    @Test
    public void setAndGetAccountTest()
    {
       //Given
       account1 = new Account();
       account1.setId(1L);
       account1.setAccountType(AccountType.INVESTMENT);
       expectedAccount = new HashSet<Account>() ;
       expectedAccount.add(account1);
       //When
       user.setAccounts(expectedAccount);
       //Then
       Set<Account> actualAccount = user.getAccounts();
       Assert.assertEquals(expectedAccount.size(),actualAccount.size());
    }

    @Test
    public void setAndGetAddressTest()
    {
        //Given
         expectedAddress = new Address();
         expectedAddress.setFirstLine("First Line of Address");
         expectedAddress.setSecondLIne("Second Line of Address");

        //When
        user.setAddress(expectedAddress);
        //Then
        Address actualAddress = user.getAddress();
        Assert.assertEquals(expectedAddress,actualAddress);
    }
}
