package runner.userTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import runner.AppRunner;
import runner.entities.User;
import runner.entities.UserBuilder;
import runner.services.UserServices;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRunner.class)
public class UserServiceUnitTest {
    @Autowired
    private UserServices userServices; //Calls the mockito


    @Test
    public void readUserTest() {
        User user = new UserBuilder().setId(1L).setFirstName("First Name").setMiddleName("Middle").setLastName("Last Name").setSocialSecurity("125455").createUser();
        user.setId(1L);
        String expectedName= "First Name";
        Mockito.when(userServices.readUser(1L)).thenReturn(user);
        String testName = userServices.readUser(1L).getFirstName();
        Assert.assertEquals(expectedName, testName);
    }


    @Test
    public void createUserTest() {
        User user = new UserBuilder().setId(1L).setFirstName("First Name").setMiddleName("Middle").setLastName("Last Name").setSocialSecurity("125455").createUser();
        String expectedName= "First Name";
        Mockito.when(userServices.createUser(user)).thenReturn(new UserBuilder().createUser());
        String actualName = user.getFirstName();
        Assert.assertEquals(expectedName, actualName);

    }
    @Test
    public void deleteUserTest() {
        User user = new UserBuilder().setId(1L).setFirstName("First Name").setMiddleName("Middle").setLastName("Last Name").setSocialSecurity("125455").createUser();
        Boolean expected = false;
        Mockito.when(userServices.createUser(user)).thenReturn(new UserBuilder().createUser());
        Boolean actual=userServices.deleteUser(1L);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateeUserTest() throws Exception {
        User user1 = new UserBuilder().setId(1L).setFirstName("First Name").setMiddleName("Middle").setLastName("Last Name").setSocialSecurity("125455").createUser();
        String expectedUpdateName= "Update the First Name";
        Mockito.when(userServices.createUser(user1)).thenReturn(user1);
        user1.setFirstName(expectedUpdateName);
        Mockito.when(userServices.updateUser(1L ,user1)).thenReturn(user1);
        String actualUpdatedName=user1.getFirstName();
        Assert.assertEquals(expectedUpdateName, actualUpdatedName);
    }
}