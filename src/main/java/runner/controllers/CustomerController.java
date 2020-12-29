package runner.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import runner.entities.Address;
import runner.entities.Customer;
import runner.services.CustomerServices;
import java.net.URI;
import java.util.logging.Logger;

@RequestMapping("/profile")
@RestController
public class CustomerController {
    @Autowired
    private CustomerServices customerServices;

    private final static Logger logger = Logger.getLogger(CustomerController.class.getName());

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> readById(@PathVariable Long id) throws Exception {
        Customer customer =customerServices.readCustomer(id);
          if( customer == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody Customer customer) {
        customer = customerServices.createCustomer(customer);
  //Best practice is to convey the URI to the newly created resource using the Location HTTP header via Spring's ServletUriComponentsBuilder utility class.
 //This will ensure that the client has some way of knowing the URI of the newly created Poll.
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customer.getId())
                .toUri();
        responseHeaders.setLocation(newPollUri);

        return new ResponseEntity<>(customer,responseHeaders, HttpStatus.CREATED);

    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Customer> update(@RequestBody Customer customer, @PathVariable Long id) throws Exception {
        return new ResponseEntity<>(customerServices.updateCustomer(id, customer), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}/phone")
    public ResponseEntity<?> updatePhone(@RequestBody String phoneNumber,@PathVariable Long id) throws Exception {
        int response = customerServices.updateCustomerPhoneNumber(id,phoneNumber);
        if(response ==0 )
            return new ResponseEntity<>(customerServices.readCustomer(id), HttpStatus.OK);
        else if(response == 1 )
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
           return new ResponseEntity<>("Re-send the phone number", HttpStatus.BAD_REQUEST);
       // return new ResponseEntity<>(userServices.updateUserPhoneNumber(id,phoneNumber), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}/email")
    public ResponseEntity<?> updateEmail(@RequestBody String email,@PathVariable Long id) throws Exception {
        int response = customerServices.updateCustomerEmail(id,email);
        if(response ==0 )
            return new ResponseEntity<>(customerServices.readCustomer(id), HttpStatus.OK);
        else if(response == 1 )
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>("Re-send the email-d", HttpStatus.BAD_REQUEST);
        // return new ResponseEntity<>(userServices.updateUserPhoneNumber(id,phoneNumber), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}/address")
    public ResponseEntity<?> updateEmail(@RequestBody Address address, @PathVariable Long id) throws Exception {
        Customer responseCustomer= customerServices.updateCustomerAddress(id,address);
        if(responseCustomer == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(customerServices.readCustomer(id), HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        int flag=customerServices.deleteCustomer(id);
        if(flag ==0)
            return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
        else if(flag==2)
            return new ResponseEntity<>("User has account with balance", HttpStatus.OK);
        else
            return new ResponseEntity<>("No accounts/user found", HttpStatus.OK);
    }
    @GetMapping(value = "/accounts/{id}")
    public ResponseEntity<?> getAllAccounts(@PathVariable Long id){
        if(customerServices.getAllAccounts(id) == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(customerServices.getAllAccounts(id), HttpStatus.OK);
    }
}