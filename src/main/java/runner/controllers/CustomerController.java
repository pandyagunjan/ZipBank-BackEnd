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
        if(customerServices.readCustomer(id) == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(customerServices.readCustomer(id), HttpStatus.OK);
    }
    @PostMapping(value = "/create")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        customer = customerServices.createCustomer(customer);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customer.getId())
                .toUri();
        responseHeaders.setLocation(newPollUri);
        return new ResponseEntity<>(customer,responseHeaders, HttpStatus.CREATED);

        //return new ResponseEntity<>(userServices.createUser(user), HttpStatus.CREATED);
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
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(customerServices.deleteCustomer(id), HttpStatus.OK);
    }
    @GetMapping(value = "/accounts/{id}")
    public ResponseEntity<?> getAllAccounts(@PathVariable Long id){
        if(customerServices.getAllAccounts(id) == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(customerServices.getAllAccounts(id), HttpStatus.OK);
    }
}