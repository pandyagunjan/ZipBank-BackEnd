package runner.controllers;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import runner.entities.Address;
import runner.entities.Customer;
import runner.services.CustomerServices;
import runner.views.Views;
import java.net.URI;
import java.util.logging.Logger;

@RequestMapping("/profile")
@RestController
public class CustomerController {

    @Autowired
    private CustomerServices customerServices;

    private final static Logger logger = Logger.getLogger(CustomerController.class.getName());

    @JsonView(Views.Profile.class)
    @GetMapping
    public ResponseEntity<?> getCustomer() {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer =customerServices.readCustomerByLogin(currentPrincipalName);
          if( customer == null)
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody Customer customer) {
        customer = customerServices.createCustomer(customer);

        if(customer!=null) {
            //Best practice is to convey the URI to the newly created resource using the Location HTTP header
            HttpHeaders responseHeaders = new HttpHeaders();
            URI newPollUri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(customer.getId())
                    .toUri();

            responseHeaders.setLocation(newPollUri);
            return new ResponseEntity<>(customer, responseHeaders, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>("Login user name already exist", HttpStatus.CONFLICT);

            }
    @PutMapping(value = "/update")
    public ResponseEntity<Customer> update(@RequestBody Customer customer) throws Exception {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customerReturned =customerServices.readCustomerByLogin(currentPrincipalName);
        Long id = customerReturned.getId();
        return new ResponseEntity<>(customerServices.updateCustomer(id,customer), HttpStatus.OK);
    }

    @JsonView(Views.PhoneNumber.class)
    @PutMapping(value = "/update/phone")
    public ResponseEntity<?> updatePhone(@RequestBody String phoneNumber) throws Exception {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customerReturned =customerServices.readCustomerByLogin(currentPrincipalName);
        Long id = customerReturned.getId();
        int response = customerServices.updateCustomerPhoneNumber(id,phoneNumber);
        if(response ==0 )
            return new ResponseEntity<>(customerServices.readCustomer(id), HttpStatus.OK);
        else if(response == 1 )
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        else
           return new ResponseEntity<>("Incorrect format of Phone , please re-send", HttpStatus.BAD_REQUEST);

    }

    @JsonView(Views.Email.class)
    @PutMapping(value = "/update/email")
    public ResponseEntity<?> updateEmail(@RequestBody String email) throws Exception {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customerReturned =customerServices.readCustomerByLogin(currentPrincipalName);
        Long id = customerReturned.getId();
        int response = customerServices.updateCustomerEmail(id,email);
        if(response ==0 )
            return new ResponseEntity<>(customerServices.readCustomer(id), HttpStatus.OK);
        else if(response == 1 )
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>("Incorrect email id format,please re-send", HttpStatus.BAD_REQUEST);

    }

    @JsonView(Views.Address.class)
    @PutMapping(value = "/update/address")
    public ResponseEntity<?> updateEmail(@RequestBody Address address) throws Exception {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customerReturned =customerServices.readCustomerByLogin(currentPrincipalName);
        Long id = customerReturned.getId();
        Customer responseCustomer= customerServices.updateCustomerAddress(id,address);
        if(responseCustomer == null)
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(customerServices.readCustomer(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteById() {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customerReturned =customerServices.readCustomerByLogin(currentPrincipalName);
        Long id = customerReturned.getId();
        int flag=customerServices.deleteCustomer(id);
        if(flag ==0)
            return new ResponseEntity<>("User has been deleted as all accounts has balance as Zero.", HttpStatus.OK);
        else if(flag==2)
            return new ResponseEntity<>("User has account with balance , cannot be deleted", HttpStatus.FORBIDDEN);
        else
            return new ResponseEntity<>("No accounts/user found", HttpStatus.NOT_FOUND);
    }

}