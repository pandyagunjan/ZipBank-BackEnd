package runner.views;

public class Views {

    public static interface AccountNumber{

    }

    public static interface AccountType{

    }

    public static interface RoutingNumber{

    }

    public static interface AccountOpening{

    }

    public static class AllAccounts implements AccountNumber, AccountType, RoutingNumber, AccountActions, AccountOpening{
        /*
        payload: multiple accounts: account number, account balance, account type, routingNumber, AccountOpening,
         */
    }

    public static interface AccountActions{
        /*
        payload: account balance
         */
    }

    public static class AccountDetails implements AccountNumber, AccountType, AccountActions, AccountOpening, RoutingNumber{
        /*
        payload: account balance, interest rate, date of creation, account number, routing number, account type, transactions
         */
    }

    public static class AccountSpecific implements AccountActions, AccountNumber{
        /*
        payload: account balance, transactions, account number
         */
    }

    public static interface Email{
    }

    public static interface PhoneNumber{
    }

    public static interface Address{
    }

    public static class Profile implements Email, PhoneNumber, Address {
        //everything in Customer except for SSN, password
    }

}
