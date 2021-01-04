package runner.views;

public class Views {

    public static interface AccountNumber{

    }

    public static interface AccountType{

    }

    public static class AllAccounts implements AccountNumber, AccountType{
        /*
        payload: multiple accounts: account number, account balance, account type
         */
    }

    public static interface AccountActions{
        /*
        payload: account balance
         */
    }

    public static class AccountDetails implements AccountNumber, AccountType, AccountActions{
        /*
        payload: account balance, interest rate, date of creation, account number, routing number, account type
         */
    }

    public static class AccountSpecific implements AccountActions{
        /*
        payload: account balance, transactions
         */
    }

    public static interface Email{
    }

    public static interface PhoneNumber{
    }

    public static interface Address{
    }

    public static class Profile implements Email, PhoneNumber, Address {
    }

}
