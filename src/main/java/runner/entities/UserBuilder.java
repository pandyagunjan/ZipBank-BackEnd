package runner.entities;

public class UserBuilder {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String socialSecurity;

    public UserBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
        return this;
    }

    public User createUser() {
        return new User(id, firstName, middleName, lastName, socialSecurity);
    }
}