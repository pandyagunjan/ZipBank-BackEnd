package runner.entities;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import runner.AppRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRunner.class)
@TestPropertySource(properties = {
        "DB_USER=newuser", "DB_PASS=password", "DB_URL=jdbc:mysql://localhost:3306/moneymanagement"
})
public class AccountEntityTest {

}

