package domain;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "Accounts")
public class Account extends Entity<String>{

    private String password;
    private AccountRole role;
    private CompanyEmployee employee;


    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    public AccountRole getRole() {
        return role;
    }

    public void setRole(AccountRole role) {
        this.role = role;
    }

    @OneToOne
    @JoinColumn(name = "employeeId")
    public CompanyEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(CompanyEmployee employee) {
        this.employee = employee;
    }

    @Override
    @Id
    @Column(name = "id")
    public String getId() {
        return super.getId();
    }

    @Override
    public void setId(String s) {
        super.setId(s);
    }
}
