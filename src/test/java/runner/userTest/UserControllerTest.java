package runner.userTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import runner.controllers.UserController;
import runner.entities.User;
import runner.entities.UserBuilder;

import javax.xml.ws.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserController userController;

    private User user;

    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        userController = Mockito.mock(UserController.class);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        this.user = new UserBuilder().setId(1L).setFirstName("First Name").setMiddleName("Middle").setLastName("Last Name").setSocialSecurity("125455").createUser();

    }

    @Test
    public void findUserTest() throws Exception {

        Mockito.when(userController.readById(1L)).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));
        mockMvc.perform(get("/user/read/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("First Name")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void createUserTest() throws Exception {
        User user1 = new UserBuilder().setId(2L).setFirstName("Gunjan").setMiddleName("S").setLastName("Pandya").setSocialSecurity("454545").createUser();
        String jsonRequest = objectMapper.writeValueAsString(user1);
       // Mockito.when(userController.create(jsonRequest)).thenReturn(new ResponseEntity<>(user1, HttpStatus.CREATED));
        MvcResult result = mockMvc.perform(post("/user/create")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

       // System.out.println(result);

        String resultContext = result.getResponse().getContentAsString();

        Response response = objectMapper.readValue(resultContext,Response.class);
        //Assert.assertTrue(response.isStatus()==Boolean.TRUE);

    }


}
