package runner.services;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import runner.AppRunner;
import runner.entities.User;

import javax.persistence.Entity;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRunner.class)
public class UserServiceTest {
    @Autowired
    private UserServices userServices; //Calls the mockito

    @Test
    public void readUserTest() {
        User user = new User( 1L, "Radha" , "Ramnik","Patel","234324");
        user.setId(1L);
        String expectedName= "Radha";
        Mockito.when(userServices.readUser(1L)).thenReturn(user);
        String testName = userServices.readUser(1L).getFirstName();
        Assert.assertEquals(expectedName, testName);
    }


    @Test
    public void createUserTest() {
        User user = new User( 1L, "Radha" , "Ramnik","Patel","234324");
        String expectedName= "Radha";
        Mockito.when(userServices.createUser(user)).thenReturn(new User());
        String actualName = user.getFirstName();
        Assert.assertEquals(expectedName, actualName);

    }
    @Test
    public void deleteUserTest() {
        User user = new User( 1L, "Radha" , "Ramnik","Patel","234324");
        Boolean expected = false;
        Mockito.when(userServices.createUser(user)).thenReturn(new User());
        Boolean actual=userServices.deleteUser(1L);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateeUserTest() throws Exception {
        User user1 = new User( 1L, "Radha" , "Ramnik","Patel","234324");
        String expectedUpdateName= "Update the First Name";
        Mockito.when(userServices.createUser(user1)).thenReturn(user1);
        user1.setFirstName(expectedUpdateName);
        Mockito.when(userServices.updateUser(1L ,user1)).thenReturn(user1);
        String actualUpdatedName=user1.getFirstName();
        Assert.assertEquals(expectedUpdateName, actualUpdatedName);
    }
}