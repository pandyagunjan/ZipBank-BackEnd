package runner.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import runner.AppRunner;
import runner.entities.Customer;
import runner.entities.Login;
import runner.security.controllers.AuthenticationController;
import runner.security.utilities.JwtUtil;
import runner.services.LoginServices;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @MockBean
            LoginServices loginServices;
    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void before(){
        SecurityContextHolder.clearContext();
    }

    @After
    public void after(){
        SecurityContextHolder.clearContext();
    }

    @Test
    public void notAuthenticatedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/myaccount/test")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void AuthenticatedTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/myaccount/test"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGenerateToken() throws Exception{
        UserDetails user = new User("user2","password", new ArrayList<>());
        String token = jwtUtil.generateToken(user); //bypassing spring security authentication and generate token
        mockMvc.perform(MockMvcRequestBuilders.get("/myaccount/test")
                .header("Authorization", "Bearer "+ token))
                .andExpect(status().isOk());
    }
}
