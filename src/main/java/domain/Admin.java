package domain;


import javax.persistence.DiscriminatorValue;

@javax.persistence.Entity
@DiscriminatorValue("Admin")
public class Admin extends CompanyEmployee{
}
