package runner.services;
import com.mifmif.common.regex.Generex;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import runner.entities.Account;
import runner.entities.Address;
import runner.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runner.entities.Login;
import runner.repositories.CustomerRepo;
import runner.repositories.LoginRepo;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CustomerServices {
   //object for logging information,warning,error
    private final static Logger loggerService = Logger.getLogger(CustomerServices.class.getName());
    private CustomerRepo customerRepo;
   //Autowired the customerService
    @Autowired
    public CustomerServices(CustomerRepo customerRepo) {
        loggerService.log(Level.INFO, "The repository for customer has been autowired to services");
        this.customerRepo = customerRepo;
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //save the customer in the DB
    public Customer createCustomer(Customer customer) {
        loggerService.log(Level.INFO, "The customer information is being checked");
        if(!checkLogin(customer.getLogin())) {
            customer.getLogin().setPassword(bCryptPasswordEncoder.encode(customer.getLogin().getPassword())); //encrypts the password before saving
            customer.setSocialSecurity(bCryptPasswordEncoder.encode(customer.getSocialSecurity()));
            //get the accounts from the customer and set the customer id in accounts table before saving the Customer.
            customer.getAccounts().stream().forEach(account -> account.setCustomer(customer));
            loggerService.log(Level.INFO, "The customer information is being saved" + checkLogin(customer.getLogin()));
            return (Customer) customerRepo.save(customer);
        }
        return null;
    }
    public Boolean checkLogin(Login login) {

        List<String> logins= customerRepo.findAllLoginsNative();
        long count = logins.stream().filter(name -> name.equalsIgnoreCase(login.getUsername())).count();
        return count!=0 ? true:false;
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


    public Customer readCustomerByLogin(String name) {
        loggerService.log(Level.INFO, "The customer information is being read");
       // Login login =
        Customer customer = customerRepo.findCustomerByLoginUsername(name);
        if (customer != null) {
            loggerService.log(Level.INFO, "The customer is found and being returned");
            return customer;
        } else {
            loggerService.log(Level.WARNING, "The customer could not be found, returned null");
            return null;
        }
    }

    public int deleteCustomer(Long id) {
        Customer customer = customerRepo.findCustomerById(id);
        Set<Account> accounts; // To collect all accounts belonging to this customer
        List<Account> result ; //To collect accounts with balance greater than 0
        if (customer != null) {
            accounts=getAllAccounts(id);
            result = accounts.stream().filter((account) -> account.getBalance() > 0).collect(Collectors.toList());
            loggerService.log(Level.INFO, "Account list size " + result.size());
            if(result.size()==0 && accounts.size()!=0) {
                customerRepo.delete(customer);
                loggerService.log(Level.INFO, "User has been deleted as account balance for all accounts =0");
                return 0;
            }
            else if(result.size()>0) {
                loggerService.log(Level.WARNING, "The customer had a balance greater than 0 and could not remove the account # " + id);
                return 2;
            }
        }
       return 1;
    }

    public Customer updateCustomer(Long id, Customer customer) throws Exception {
        loggerService.log(Level.INFO, "Finding the customer to be updated");
        Customer customerFromDB = customerRepo.findCustomerById(id);
        Set<Account> accountSetFromDB ;
        if (customerFromDB != null) {
            loggerService.log(Level.INFO, "Customer with id to be updated found " + customerFromDB.getId());
            customer.getAddress().setId(customerFromDB.getAddress().getId());
            customer.setId(customerFromDB.getId());
            accountSetFromDB = customerFromDB.getAccounts();
            //Once the existing accounts are added , we will add more accounts
            for (Account account : customer.getAccounts()) {
                account.setCustomer(customerFromDB);
                account.setEncryptedUrl(generateRandomUrl());
                accountSetFromDB.add(account);
            }
            customer.setAccounts(accountSetFromDB);

            customerRepo.save(customer);
            loggerService.log(Level.INFO, "Customer with Id " + customerFromDB.getId() + "has been updated");
            return customerFromDB;
        } else {
            loggerService.log(Level.WARNING, "Customer with Id " + id + "not found in db");
            throw new Exception("id not found to be udapted");
        }
    }


    public int updateCustomerPhoneNumber(Long id, String phone) throws ParseException {
        loggerService.log(Level.INFO, "Finding the customer to be updated");
        Customer customer = customerRepo.findCustomerById(id);
        Pattern pattern = Pattern.compile("([0-9]{10}|\\(?[0-9]{3}\\)?[- ]{0,1}[0-9]{3}[- ]{0,1}[0-9]{4})");
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
        //return 0 when updated , 1 is customer not found and 2 if value does not match the REGEX
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
        if (customer != null) {
            address.setId(customer.getAddress().getId());
            loggerService.log(Level.INFO, "customer with id "+id+ " found to be updated");
            customer.setAddress(address);
            customerRepo.save(customer);
            loggerService.log(Level.INFO, "customer with Id " + customer.getId() + " address has been updated");
            return customer;
        }
        return null;
    }

    //Delete if not needed
    public Set<Account> getAllAccounts(Long id) {
        loggerService.log(Level.INFO, "Finding the customer to get all accounts");
        Customer customer = customerRepo.findCustomerById(id);

        if (customer != null) {
            loggerService.log(Level.INFO, "Accounts belonging to "+id+ " use has been found ,accounts are being returned");
             return customer.getAccounts();
        }
        return null;
    }

    //superceded by Set<Account> findAccountsByCustomer_LoginUsername (String login) in AccountRepo
/*    public Set<Account> getAllAccounts(String username) {
        loggerService.log(Level.INFO, "Finding the customer to get all accounts");
        Login login = loginRepo.findLoginByUsername(username);
        Customer customer = customerRepo.findCustomerById(login.getId());

        if (customer != null) {
            loggerService.log(Level.INFO, "Accounts belonging to "+login.getId()+ " use has been found ,accounts are being returned");
            return customer.getAccounts();
        }
        return null;
    }*/

    //generate 35-40 random characters
    public String generateRandomUrl() {
        Generex generex = new Generex("[A-Za-z0-9]{35,40}");
        String randomString = generex.random();
        return randomString;
    }
}