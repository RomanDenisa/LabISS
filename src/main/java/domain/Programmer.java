package domain;


import javax.persistence.DiscriminatorValue;

@javax.persistence.Entity
@DiscriminatorValue("Programmer")
public class Programmer extends CompanyEmployee {
}
