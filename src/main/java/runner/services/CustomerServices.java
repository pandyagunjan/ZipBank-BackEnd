package runner.services;
import runner.entities.Account;
import runner.entities.Address;
import runner.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runner.repositories.CustomerRepo;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class CustomerServices {
   //object for logging information,warning,error
    private final static Logger loggerService = Logger.getLogger(CustomerServices.class.getName());

    private CustomerRepo customerRepo;
   //Autowired the customerService
    @Autowired
    public CustomerServices(CustomerRepo customerRepo) {
        loggerService.log(Level.INFO, "The repository for customer is autowired to services");
        this.customerRepo = customerRepo;
    }

    //save the customer in the DB
    public Customer createCustomer(Customer customer) {
        loggerService.log(Level.INFO, "The customer information is being saved");
        return (Customer) customerRepo.save(customer);
    }

    public Customer readCustomer(Long id) {
        loggerService.log(Level.INFO, "The customer information is being read");
        Customer customer = customerRepo.findCustomerById(id);
        if (customer != null) {
            loggerService.log(Level.INFO, "The customer is found and being returned");
            return customer;
        } else {
            loggerService.log(Level.WARNING, "The customer could not be found, returned null");
            return null;
        }
    }

    public Boolean deleteCustomer(Long id) {
        Customer customerFromDB = customerRepo.findCustomerById(id);
        loggerService.log(Level.INFO, "The customer to be deleted has been found " + customerFromDB.getId());
        customerRepo.delete(customerFromDB);
        loggerService.log(Level.WARNING, "The customer has been deleted ,returning flag based on IfExist check");
        return !customerRepo.existsById(id);
    }

    public Customer updateCustomer(Long id, Customer customer) throws Exception {
        loggerService.log(Level.INFO, "Finding the customer to be updated");
        Customer customerFromDB = customerRepo.findCustomerById(id);
        Set<Account> accountSetFromDB = new HashSet<>();
        if (customerFromDB != null) {
            loggerService.log(Level.INFO, "Customer with id to be updated found " + customerFromDB.getId());
            customer.getAddress().setId(customerFromDB.getAddress().getId());
            customerFromDB.setAddress(customer.getAddress());
            customerFromDB.setFirstName(customer.getFirstName());
            customerFromDB.setMiddleName(customer.getMiddleName());
            customerFromDB.setLastName(customer.getLastName());
            customerFromDB.setDateOfBirth(customer.getDateOfBirth());
            customerFromDB.setSocialSecurity(customer.getSocialSecurity());
            customerFromDB.setEmail(customer.getEmail());
            customerFromDB.setPhoneNumber(customer.getPhoneNumber());
            accountSetFromDB = customerFromDB.getAccounts();
            //Once the existing accounts are added , we will add more accounts
            for (Account account : customer.getAccounts()) {
                accountSetFromDB.add(account);
            }
            customerFromDB.setAccounts(accountSetFromDB);

            customerRepo.save(customerFromDB);
            loggerService.log(Level.INFO, "Customer with Id " + customerFromDB.getId() + "has been updated");
            return customerFromDB;
        } else {
            loggerService.log(Level.SEVERE, "Customer with Id " + id + "not found in db");
            throw new Exception("id not found to be udapted");
        }
    }


    public int updateCustomerPhoneNumber(Long id, String phone) throws ParseException {
        loggerService.log(Level.INFO, "Finding the customer to be updated");
        Customer customer = customerRepo.findCustomerById(id);
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phone);

        if (matcher.matches()) {
            if (customer != null) {
                loggerService.log(Level.INFO, "Customer with id " + customer.getId() + " found to be updated");
                customer.setPhoneNumber(phone);
                customerRepo.save(customer);
                loggerService.log(Level.INFO, "User with Id " + customer.getId() + " phone number has been updated");
                return 0;
            } else {
                return 1;
            }
        }
        return 2;
    }

    public int updateCustomerEmail(Long id, String email) {
        loggerService.log(Level.INFO, "Finding the user to be updated");
        Customer customer = customerRepo.findCustomerById(id);


        Pattern patternEmail = Pattern.compile("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = patternEmail.matcher(email);

        if (matcher.matches()) {
            if (customer != null) {
                loggerService.log(Level.INFO, "customer with id " + customer.getId() + " found to be updated");
                customer.setEmail(email);
                customerRepo.save(customer);
                loggerService.log(Level.INFO, "customer with Id " + customer.getId() + " email id has been updated");
                return 0;
            } else {
                return 1;
            }
        }
        return 2;
    }

    public Customer updateCustomerAddress(Long id, Address address) {
        loggerService.log(Level.INFO, "Finding the customer to be updated");
        Customer customer = customerRepo.findCustomerById(id);
        address.setId(customer.getAddress().getId());
        if (customer != null) {
            loggerService.log(Level.INFO, "customer with id "+id+ " found to be updated");
            customer.setAddress(address);
            customerRepo.save(customer);
            loggerService.log(Level.INFO, "customer with Id " + customer.getId() + " address has been updated");
            return customer;
        }
        return null;
    }

    public Set<Account> getAllAccounts(Long id) {
        loggerService.log(Level.INFO, "Finding the customer to get all accounts");
        Customer customer = customerRepo.findCustomerById(id);

        if (customer != null) {
            loggerService.log(Level.INFO, "customer with id "+id+ " found ,account being returned");
             return customer.getAccounts();
        }
        return null;

    }
}