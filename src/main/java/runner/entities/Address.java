package runner.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Address {
    @Id
    @Column(name = "userId") //references userID from user
    private Long id;
    @Column(nullable = false)
    private String firstLine;
    @Column(nullable = true)
    private String secondLIne;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String zipcode;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public void setFirstLine(String firstLine) {
        this.firstLine = firstLine;
    }

    public String getSecondLIne() {
        return secondLIne;
    }

    public void setSecondLIne(String secondLIne) {
        this.secondLIne = secondLIne;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
