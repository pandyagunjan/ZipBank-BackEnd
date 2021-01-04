package runner.entities;

import com.fasterxml.jackson.annotation.JsonView;
import runner.views.Views;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonView(Views.Address.class)
    @Column(nullable = false)
    private String firstLine;
    @Column(nullable = true)
    @JsonView(Views.Address.class)
    private String secondLIne;
    @Column(nullable = false)
    @JsonView(Views.Address.class)
    private String city;
    @Column(nullable = false)
    @JsonView(Views.Address.class)
    private String state;
    @Column(nullable = false)
    @JsonView(Views.Address.class)
    private String zipcode;

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
