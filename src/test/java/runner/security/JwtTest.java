package runner.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import runner.AppRunner;
import runner.entities.Customer;
import runner.security.utilities.JwtUtil;
import runner.services.LoginServices;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = {"DB_USER=newuser",
        "DB_PASS=password",
        "DB_URL=jdbc:mysql://localhost:3306/moneymanagement"})

public class JwtTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void notAuthenticatedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account/test")).andExpect(status().isForbidden());
    }

    @Test
    public void shouldGenerateToken() throws Exception{
        UserDetails user = new User("username","password", new ArrayList<>());
        String token = jwtUtil.generateToken(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/account/test")
                .header("Authorization", token))
                .andExpect(status().isOk());
    }
}
