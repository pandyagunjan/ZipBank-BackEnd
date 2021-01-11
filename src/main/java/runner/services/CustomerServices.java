package runner.services;
import com.mifmif.common.regex.Generex;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import runner.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runner.repositories.CustomerRepo;
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
//Can we try removing this below Autowired ??
    @Autowired
    private AccountServices accountServices;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //Save the customer in the DB
    public Customer createCustomer(Customer customer) throws Exception {
        loggerService.log(Level.INFO, "The customer information is being checked");
        if(!checkLogin(customer.getLogin())) {
            customer.getLogin().setPassword(bCryptPasswordEncoder.encode(customer.getLogin().getPassword())); //encrypts the password before saving
            customer.setSocialSecurity(bCryptPasswordEncoder.encode(customer.getSocialSecurity())); //encrypts the SSN before saving
            //get the accounts from the customer and set the customer id in accounts table before saving the Customer.
            Account newAccount = customer.getAccounts().stream().findFirst().orElse(null);
            //customer.getAccounts().stream().forEach(account -> account.setCustomer(customer)); -Gunjan's method
            newAccount.setCustomer(customer);
            //passing the account to "createAccount" in accountServices to generate the account number
            accountServices.setUpAccount(newAccount);
            //doing the deposit from the source account;
            accountServices.transferMoneyToNewAccount(newAccount);
            loggerService.log(Level.INFO, "The customer information is being saved" + checkLogin(customer.getLogin()));
            return customerRepo.save(customer);
        }
        return null;
    }

    //Check if the username already exist
    public Boolean checkLogin(Login login) {
        List<String> logins= customerRepo.findAllLoginsNative();
        long count = logins.stream().filter(name -> name.equalsIgnoreCase(login.getUsername())).count();
        return count!=0 ? true:false;
    }

    //Find the customer from DB using id
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

    //Find Customer from DB using the logged in user name
    public Customer readCustomerByLogin(String name) {
        loggerService.log(Level.INFO, "The customer information is being read");
        Customer customer = customerRepo.findCustomerByLoginUsername(name);
        if (customer != null) {
            loggerService.log(Level.INFO, "The customer is found and being returned");
            return customer;
        } else {
            loggerService.log(Level.WARNING, "The customer could not be found, returned null");
            return null;
        }
    }
   //Delete the customer from DB after checking that all account balance are < 0
   //Returns 0 = Deleted , 1 : Customer not found , 2 : Accounts with > 0 balance exist
    public int deleteCustomer(Long id) {
        Customer customer = customerRepo.findCustomerById(id);
        if (customer != null) {
            if (customer.getAccounts() != null) {
                return checkAccountBalanceAndDelete(id, customer);
            }
            else
            {
                customerRepo.delete(customer);
                loggerService.log(Level.INFO, "User has been deleted as no accounts found for this customer");
                return 0;
            }
        }
        loggerService.log(Level.WARNING, "The customer not found" + id);
       return 1;
    }

  // Used in above method to decide if the customer can be deleted
  int checkAccountBalanceAndDelete(Long id, Customer customer) {
        Set<Account> accounts; // To collect all accounts belonging to this customer
        List<Account> result ; //To collect accounts with balance greater than 0
        accounts = getAllAccounts(id);
        result = accounts.stream().filter((account) -> account.getBalance() > 0).collect(Collectors.toList());
        loggerService.log(Level.INFO, "Account list size " + result.size());
        if (result.size() == 0 && accounts.size() != 0) {
            customerRepo.delete(customer);
            loggerService.log(Level.INFO, "User has been deleted as account balance for all accounts 0 , All accounts are deleted as well.");
            return 0;
        } else if (result.size() > 0) {
            loggerService.log(Level.WARNING, "The customer had a balance greater than 0 and could not remove the account # " + id);
            return 2;
        }
        return 4;
    }

    //Update the Customer (all fields) in the DB ,based on body of request
    public Customer updateCustomer(Long id, Customer customer) throws Exception {
        loggerService.log(Level.INFO, "Finding the customer to be updated");
        Customer customerFromDB = customerRepo.findCustomerById(id);
        Set<Account> accountSetFromDB ;
        if (customerFromDB != null) {
            loggerService.log(Level.INFO, "Customer with id to be updated found " + customerFromDB.getId());
            customer.getAddress().setId(customerFromDB.getAddress().getId());
            customer.setId(customerFromDB.getId());
            accountSetFromDB = customerFromDB.getAccounts();
            //Once the existing accounts are added , we will add more accounts from the request body
            for (Account account : customer.getAccounts()) {
                account.setCustomer(customerFromDB);
                account.setEncryptedUrl(accountServices.generateRandomUrl()); // test-case fails as need to autowire this too.
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

    //Update phone number ,check syntax based on the REGEX
    //Returns 0 = Updated , 1 : Customer not found , 2 : Phone number format not correct
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
                return 0; // Phone number has been updated
            } else {
                return 1; // Customer not found
            }
        }
        return 2; // Phone number format is incorrect
    }

    //Update Email number ,check syntax based on the REGEX
    //Returns 0 = Updated , 1 : Customer not found , 2 : Email format not correct
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
                return 0; // Updated
            } else {
                return 1; // Customer not found
            }
        }
        return 2; // Email format incorrect
    }
    //Update Customer address
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

}