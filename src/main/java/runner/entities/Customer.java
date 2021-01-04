package runner.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import runner.views.Views;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;


@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonView(Views.Profile.class)
    @Column(nullable = false)
    private String firstName;

    @JsonView(Views.Profile.class)
    private String middleName;
    @JsonView(Views.Profile.class)
    @Column(nullable = false)
    private String lastName;
    @JsonView(Views.Profile.class)
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String socialSecurity;

    @Column(nullable = false)
    @JsonView(Views.Email.class)
    private String email;

    @JsonView(Views.PhoneNumber.class)
    @Column(nullable = false)
    private String phoneNumber;

    @JsonView(Views.Address.class)
    @OneToOne(cascade = ALL, fetch = FetchType.EAGER)
    private Address address;
    @JsonBackReference(value = "login")
    @OneToOne(mappedBy = "customer", cascade = ALL,fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn //sharing primary key with user login since creating a new user requires a login anyways
    private Login login;

    @OneToMany(mappedBy = "customer", cascade = ALL,fetch = FetchType.EAGER)
    @OrderBy
    @JsonBackReference
    private Set<Account> accounts;

    public Customer() {
    }

    public Customer(Long id, String firstName, String middleName, String lastName, String socialSecurity) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.socialSecurity = socialSecurity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)  {

        this.phoneNumber =   phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }


}