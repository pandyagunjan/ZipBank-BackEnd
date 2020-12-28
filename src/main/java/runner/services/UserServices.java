package runner.services;
import runner.entities.Account;
import runner.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runner.repositories.UserRepo;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserServices {
    //CRUD methods
    private final static Logger loggerService = Logger.getLogger(UserServices.class.getName());

    private UserRepo userRepo;
    @Autowired
    public UserServices(UserRepo userRepo)
    {
        loggerService.log(Level.INFO, "The repository for user is autowired to services");
        this.userRepo=userRepo;
    }
    public User createUser(User user)
    {
        loggerService.log(Level.INFO, "The user information is being saved");
        return (User) userRepo.save(user);
    }

    public User readUser(Long id)
    {
        loggerService.log(Level.INFO, "The user information is being read");
        User user = userRepo.findUserById(id);
        if(user !=null)
        {
            loggerService.log(Level.INFO, "The user is found and being retured");
            return user;
        }
        else
        {
            loggerService.log(Level.WARNING, "The user could not be found, returned null");
            return null;
        }
    }

    public Boolean deleteUser(Long id)
    {
        User userFromDB = userRepo.findUserById(id);
        loggerService.log(Level.INFO, "The user to be deleted has been found "+ userFromDB.getId());
        userRepo.delete(userFromDB);
        loggerService.log(Level.WARNING, "The user has been deleted has been found,returning flag based on IfExist check");
        return !userRepo.existsById(id);
    }

    public User updateUser(Long id ,User user) throws Exception {
        loggerService.log(Level.INFO, "Finding the user to be updated");
        User userFromDB = userRepo.findUserById(id);
        loggerService.log(Level.INFO, "User with id to be updated found " + userFromDB.getId() );
        Set<Account> accountSetFromDB = new HashSet<>();
        if(userFromDB !=null) {

            userFromDB.setFirstName(user.getFirstName());
            userFromDB.setMiddleName(user.getMiddleName());
            userFromDB.setLastName(user.getLastName());
            userFromDB.setDateOfBirth(user.getDateOfBirth());
            userFromDB.setSocialSecurity(user.getSocialSecurity());
            userFromDB.setEmail(user.getEmail());
            userFromDB.setPhoneNumber(user.getPhoneNumber());
            accountSetFromDB = userFromDB.getAccounts();
            for(Account account:user.getAccounts())
            {
                accountSetFromDB.add(account);
            }
            userFromDB.setAccounts(accountSetFromDB);
            userRepo.save(userFromDB);
            loggerService.log(Level.INFO, "User with Id " + userFromDB.getId() + "has been updated");
            return userFromDB;
        }
        else {
            loggerService.log(Level.SEVERE, "User with Id " + userFromDB.getId() + "not found in db");
            throw new Exception("id not found to be udapted");
        }
    }


    public int updateUserPhoneNumber(Long id , String phone) {
        loggerService.log(Level.INFO, "Finding the user to be updated");
        User userFromDB = userRepo.findUserById(id);
        loggerService.log(Level.INFO, "User with id " + userFromDB.getId() + " found to be updated");

        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phone);

        if (matcher.matches()) {
            if (userFromDB != null) {
                userFromDB.setPhoneNumber(phone);
                userRepo.save(userFromDB);
                loggerService.log(Level.INFO, "User with Id " + userFromDB.getId() + " phone number has been updated");
                return 0;
            } else {
                return 1;
            }
        }
        return 2;
    }

    public int updateUserEmailId(Long id , String email) {
        loggerService.log(Level.INFO, "Finding the user to be updated");
        User userFromDB = userRepo.findUserById(id);
        loggerService.log(Level.INFO, "User with id " + userFromDB.getId() + " found to be updated");

        Pattern patternEmail = Pattern.compile("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$",Pattern.CASE_INSENSITIVE);
        Matcher matcher = patternEmail.matcher(email);

        if (matcher.matches()) {
            if (userFromDB != null) {
                userFromDB.setEmail(email);
                userRepo.save(userFromDB);
                loggerService.log(Level.INFO, "User with Id " + userFromDB.getId() + " email id has been updated");
                return 0;
            } else {
                return 1;
            }
        }
        return 2;
    }
}