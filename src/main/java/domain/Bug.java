package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name="Bugs")
public class Bug extends Entity<Integer>{

    private String name;
    private String description;
    private BugStatus bugStatus;
    private String date;
    private String additionalInfo;
    private Tester tester;
    private Programmer programmer;

    public Bug(){
    }

    public Bug(String name, String description, BugStatus bugStatus, String date) {
        this.name = name;
        this.description = description;
        this.bugStatus = bugStatus;
        this.date = date;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public BugStatus getBugStatus() {
        return bugStatus;
    }

    public void setBugStatus(BugStatus bugStatus) {
        this.bugStatus = bugStatus;
    }

    @Column(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Column(name = "additionalInfo")
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    @Id
    @GeneratedValue( generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "bugId")
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer integer) {
        super.setId(integer);
    }

    @ManyToOne
    public Tester getTester() {
        return tester;
    }

    public void setTester(Tester tester) {
        this.tester = tester;
    }

    @ManyToOne
    public Programmer getProgrammer() {
        return programmer;
    }

    public void setProgrammer(Programmer programmer) {
        this.programmer = programmer;
    }
}
