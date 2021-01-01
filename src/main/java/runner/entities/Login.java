package runner.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Login implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @JsonBackReference(value = "customer")
    @OneToOne
    @PrimaryKeyJoinColumn
    private Customer customer;

    public Login(){
    }

    public Login(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customer getUser() {
        return customer;
    }

    public void setUser(Customer customer) {
        this.customer = customer;
    }

    //is user account expired
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //is user account locked
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //is user credential expired
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //is user enabled
    @Override
    public boolean isEnabled() {
        return true;
    }

    //ignore this, not using authorities
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
