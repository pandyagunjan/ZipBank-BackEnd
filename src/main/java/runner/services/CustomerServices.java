package runner.services;
import runner.entities.Account;
import runner.entities.Address;
import runner.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runner.repositories.CustomerRepo;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class CustomerServices {
    //CRUD methods
    private final static Logger loggerService = Logger.getLogger(CustomerServices.class.getName());

    private CustomerRepo customerRepo;

    @Autowired
    public CustomerServices(CustomerRepo customerRepo) {
        loggerService.log(Level.INFO, "The repository for user is autowired to services");
        this.customerRepo = customerRepo;
    }

    public Customer createCustomer(Customer customer) {
        loggerService.log(Level.INFO, "The user information is being saved");
        return (Customer) customerRepo.save(customer);
    }

    public Customer readCustomer(Long id) {
        loggerService.log(Level.INFO, "The user information is being read");
        Customer customer = customerRepo.findCustomerById(id);
        if (customer != null) {
            loggerService.log(Level.INFO, "The user is found and being retured");
            return customer;
        } else {
            loggerService.log(Level.WARNING, "The user could not be found, returned null");
            return null;
        }
    }

    public Boolean deleteCustomer(Long id) {
        Customer customerFromDB = customerRepo.findCustomerById(id);
        loggerService.log(Level.INFO, "The user to be deleted has been found " + customerFromDB.getId());
        customerRepo.delete(customerFromDB);
        loggerService.log(Level.WARNING, "The user has been deleted has been found,returning flag based on IfExist check");
        return !customerRepo.existsById(id);
    }

    public Customer updateCustomer(Long id, Customer customer) throws Exception {
        loggerService.log(Level.INFO, "Finding the user to be updated");
        Customer customerFromDB = customerRepo.findCustomerById(id);
        loggerService.log(Level.INFO, "User with id to be updated found " + customerFromDB.getId());
        Set<Account> accountSetFromDB = new HashSet<>();
        if (customerFromDB != null) {

            customerFromDB.setFirstName(customer.getFirstName());
            customerFromDB.setMiddleName(customer.getMiddleName());
            customerFromDB.setLastName(customer.getLastName());
            customerFromDB.setDateOfBirth(customer.getDateOfBirth());
            customerFromDB.setSocialSecurity(customer.getSocialSecurity());
            customerFromDB.setEmail(customer.getEmail());
            customerFromDB.setPhoneNumber(customer.getPhoneNumber());
            accountSetFromDB = customerFromDB.getAccounts();
            for (Account account : customer.getAccounts()) {
                accountSetFromDB.add(account);
            }
            customerFromDB.setAccounts(accountSetFromDB);
            customerRepo.save(customerFromDB);
            loggerService.log(Level.INFO, "User with Id " + customerFromDB.getId() + "has been updated");
            return customerFromDB;
        } else {
            loggerService.log(Level.SEVERE, "User with Id " + customerFromDB.getId() + "not found in db");
            throw new Exception("id not found to be udapted");
        }
    }


    public int updateCustomerPhoneNumber(Long id, String phone) {
        loggerService.log(Level.INFO, "Finding the user to be updated");
        Customer customerFromDB = customerRepo.findCustomerById(id);
        loggerService.log(Level.INFO, "User with id " + customerFromDB.getId() + " found to be updated");

        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phone);

        if (matcher.matches()) {
            if (customerFromDB != null) {
                customerFromDB.setPhoneNumber(phone);
                customerRepo.save(customerFromDB);
                loggerService.log(Level.INFO, "User with Id " + customerFromDB.getId() + " phone number has been updated");
                return 0;
            } else {
                return 1;
            }
        }
        return 2;
    }

    public int updateCustomerEmail(Long id, String email) {
        loggerService.log(Level.INFO, "Finding the user to be updated");
        Customer customerFromDB = customerRepo.findCustomerById(id);
        loggerService.log(Level.INFO, "User with id " + customerFromDB.getId() + " found to be updated");

        Pattern patternEmail = Pattern.compile("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = patternEmail.matcher(email);

        if (matcher.matches()) {
            if (customerFromDB != null) {
                customerFromDB.setEmail(email);
                customerRepo.save(customerFromDB);
                loggerService.log(Level.INFO, "User with Id " + customerFromDB.getId() + " email id has been updated");
                return 0;
            } else {
                return 1;
            }
        }
        return 2;
    }

    public Customer updateCustomerAddress(Long id, Address address) {

        loggerService.log(Level.INFO, "Finding the user to be updated");
        Customer customerFromDB = customerRepo.findCustomerById(id);
        loggerService.log(Level.INFO, "User with id " + customerFromDB.getId() + " found to be updated");

        if (customerFromDB != null) {
            customerFromDB.setAddress(address);
            customerRepo.save(customerFromDB);
            loggerService.log(Level.INFO, "User with Id " + customerFromDB.getId() + " address has been updated");
            return customerFromDB;
        }
        return null;
    }
}