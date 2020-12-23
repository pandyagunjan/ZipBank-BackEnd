package runner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import runner.entities.User;
import runner.services.UserServices;

import java.time.LocalDate;
//import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRunner.class)
public class UserServiceUnitTest {

   // @Autowired
   // private UserServices userServices;
    UserServices mock = Mockito.mock(UserServices.class);

    @Test
    public void whenUserIsProvided_thenRetrievedUserIsCorrect() {

        User user = new User(1L,"First Name", "Last name Test",  "234234", "Address");
        user.setId(1L);
        String expectedName= "First Name";

        Mockito.when(mock.readUser(1L)).thenReturn(user);

        String testName = mock.readUser(1L).getFirstName();

        Assert.assertEquals(expectedName, testName);
    }

}
