package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "Employees")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class CompanyEmployee extends Entity<Integer>{

    private String firstName;
    private String lastName;

    public CompanyEmployee() {
    }

    public CompanyEmployee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Column(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    @Id
    @GeneratedValue( generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "employeeId")
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer integer) {
        super.setId(integer);
    }
}
