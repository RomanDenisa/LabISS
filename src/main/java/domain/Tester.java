package domain;

import javax.persistence.DiscriminatorValue;

@javax.persistence.Entity
@DiscriminatorValue("Tester")
public class Tester extends CompanyEmployee{

}
