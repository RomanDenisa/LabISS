package domain;

public class ReportDTO extends Entity<Integer> {

    private String firstName;
    private String lastName;
    private Integer activity;

    public ReportDTO() {
    }

    public ReportDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ReportDTO(String firstName, String lastName, Integer activity) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.activity = activity;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }
}
