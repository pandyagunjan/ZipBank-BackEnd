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


@RestController
public class CustomerController {

    @Autowired
    private CustomerServices customerServices;

    @JsonView(Views.Profile.class)
    @GetMapping(value = "/myaccount/profile")
    public ResponseEntity<?> getCustomer() {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName(); //needs JWT token in header
        Customer customer =customerServices.readCustomerByLogin(currentPrincipalName); //<< for testing on angular, need to change back to currentPrincipalName
          if( customer == null)
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(customer, HttpStatus.OK);
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

/*    @PutMapping(value = "myaccount/profile")
    public ResponseEntity<Customer> update(@RequestBody Customer customer) throws Exception {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customerReturned =customerServices.readCustomerByLogin(*//*currentPrincipalName*//* "user1");
        Long id = customerReturned.getId();
        return new ResponseEntity<>(customerServices.updateCustomer(id,customer), HttpStatus.OK);
    }*/

    @JsonView(Views.PhoneNumber.class)
    @PutMapping(value = "myaccount/profile/phone")
    public ResponseEntity<?> updatePhone(@RequestBody String phoneNumber) throws Exception {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customerReturned =customerServices.readCustomerByLogin(currentPrincipalName);

        if(customerReturned!=null) {
            Long id = customerReturned.getId();
            int response = customerServices.updateCustomerPhoneNumber(id, phoneNumber);
            if (response == 0)
                return new ResponseEntity<>(customerServices.readCustomer(id), HttpStatus.OK);
           else if (response == 1)
               return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            else if (response == 2)
                return new ResponseEntity<>("Incorrect format of Phone , please re-send", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);

    }

    @JsonView(Views.Email.class)
    @PutMapping(value = "myaccount/profile/email")
    public ResponseEntity<?> updateEmail(@RequestBody String email) {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customerReturned =customerServices.readCustomerByLogin(currentPrincipalName);
        if(customerReturned!=null) {
            Long id = customerReturned.getId();

            int response = customerServices.updateCustomerEmail(id, email);
            if (response == 0)
                return new ResponseEntity<>(customerServices.readCustomer(id), HttpStatus.OK);
            else if (response == 1)
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>("Incorrect email id format,please re-send", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
    }

    @JsonView(Views.Address.class)
    @PutMapping(value = "myaccount/profile/address")
    public ResponseEntity<?> updateEmail(@RequestBody Address address) {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customerReturned =customerServices.readCustomerByLogin(currentPrincipalName);
        if(customerReturned!=null) {
            Long id = customerReturned.getId();
            Customer responseCustomer = customerServices.updateCustomerAddress(id, address);
            if (responseCustomer == null)
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>(customerServices.readCustomer(id), HttpStatus.OK);
        }
        return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
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

}