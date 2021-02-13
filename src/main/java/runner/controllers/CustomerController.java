package runner.controllers;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import runner.entities.Address;
import runner.entities.Customer;
import runner.services.CustomerServices;
import runner.views.Views;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CustomerController {

    @Autowired
    private CustomerServices customerServices;

    @JsonView(Views.Profile.class)
    @GetMapping(value = "/myaccount/profile")
    public ResponseEntity<Customer> getCustomer() {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName(); //needs JWT token in header
        return new ResponseEntity<>(customerServices.readCustomerByLogin(currentPrincipalName), HttpStatus.OK);
    }

    @PostMapping(value = "/openaccount",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Customer customer) throws Exception {
        customer = customerServices.createCustomer(customer);

        if(customer!=null) {
            return new ResponseEntity<>(customer,  HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>("Login user name already exist", HttpStatus.CONFLICT);
    }


    @JsonView(Views.PhoneNumber.class)
    @PutMapping(value = "myaccount/profile/phone")
    public ResponseEntity<Customer> updatePhone(@RequestBody Customer customer) throws Exception {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(customerServices.updateCustomerPhoneNumber(currentPrincipalName,customer), HttpStatus.OK);
    }

    @JsonView(Views.Email.class)
    @PutMapping(value = "myaccount/profile/email")
    public ResponseEntity<Customer> updateEmail(@RequestBody Customer customer) {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
       return new ResponseEntity<>(customerServices.updateCustomerEmail(currentPrincipalName, customer), HttpStatus.OK);
    }

    @JsonView(Views.Address.class)
    @PutMapping(value = "myaccount/profile/address")
    public ResponseEntity<Customer> updateEmail(@RequestBody Address address) {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(customerServices.updateCustomerAddress(currentPrincipalName, address), HttpStatus.OK);
    }

    @DeleteMapping(value = "myaccount/profile/delete")
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

    @DeleteMapping(value = "myaccount/{encryptedUrl}/delete")
    public ResponseEntity<Customer> deleteAccount(@PathVariable String encryptedUrl) throws Exception{
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(customerServices.removeAccount(currentPrincipalName,encryptedUrl), HttpStatus.OK);
    }

}