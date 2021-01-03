package runner.controllers;

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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import runner.entities.Customer;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerController.class)
@ActiveProfiles("test")
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerController userController;

    private Customer customer;

    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        userController = Mockito.mock(CustomerController.class);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        this.customer = new Customer( 1L, "Radha" , "Ramnik","Patel","234324");
    }

//    @Test
//    public void findUserTest() throws Exception {
//        Mockito.when(userController.readById(1L)).thenReturn(new ResponseEntity<>(customer, HttpStatus.OK));
//        mockMvc.perform(get("/profile/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName", is("Radha")))
//                .andExpect(jsonPath("$.id", is(1)));
//    }

    @Test
    public void createUserTest() throws Exception {
        Customer customer1 = new Customer( 2L, "Rekha" , "Jagdish","Patel","234234324");
//        BDDMockito
//                .given(userController.create(user1))
//                .willReturn(user1);

        String jsonRequest = objectMapper.writeValueAsString(customer1);
       // Mockito.when(userController.create(customer1)).thenReturn(new ResponseEntity<>(customer1, HttpStatus.CREATED));

              MvcResult result=   mockMvc.perform(post("/profile/create")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
               // .andExpect(MockMvcResultMatchers.status().isOk())
               // .andExpect(jsonPath("$.id",is(2)))
                      .andReturn();
                //.andExpect(MockMvcResultMatchers.content().string(jsonRequest));

        MockHttpServletResponse response=result.getResponse();
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
      //  assertEquals("http://localhost:8080/profile/create/1", response.getHeader(HttpHeaders.LOCATION));



//        Mockito.when(
//                studentService.addCourse(Mockito.anyString(),
//                        Mockito.any(Course.class))).thenReturn(mockCourse);
//
//        // Send course as body to /students/Student1/courses
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/students/Student1/courses")
//                .accept(MediaType.APPLICATION_JSON).content(exampleCourseJson)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//
//        assertEquals("http://localhost/students/Student1/courses/1",
//                response.getHeader(HttpHeaders.LOCATION));

    }

}
