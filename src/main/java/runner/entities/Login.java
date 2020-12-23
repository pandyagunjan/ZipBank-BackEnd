package runner.entities;

import javax.persistence.*;

@Entity
public class Login {
    @Id
    @Column(name = "userId") //references userID from user
    private Long id;
//    @Column(nullable = false)
//    private Long userId;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private User user;

    public Login() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getUserId() { return userId;  }
//
//    public void setUserId(Long userId) {     this.userId = userId;   }

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
