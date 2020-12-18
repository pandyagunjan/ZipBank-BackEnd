package entities;

import javax.persistence.*;

@Entity
public class Login {
    @Id
    private Long Id;
    private Long userId;
    private String username;
    private String password;

    public Long getId() {
        return Id;
    }

    public Long getUserId() {   return userId;  }

    public void setUserId(Long userId) {     this.userId = userId;   }

    public void setId(Long id) {
        Id = id;
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
}
