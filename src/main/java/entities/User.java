package entities;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
public class User {
    @Id
    private Long Id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String socialSecurity;
    private String address;

    public User() {
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }


    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}