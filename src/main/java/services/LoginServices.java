package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.LoginRepo;

@Service
public class LoginServices {

    @Autowired
    private LoginRepo loginRepo;

    public Boolean checkUserCredential(String userName,String password)
    {
     //Check for username and password ,check about Tokens for user authentication

        return  true;
    }

    public Boolean logOut()
    {
        // Login for logOut
        return true;
    }

    // User authentication

    public Boolean autoLogOff()
    {
        return true;
    }


//Lock after 3 attempts
//Security questions
//Forgot username
//Forgot password

}
