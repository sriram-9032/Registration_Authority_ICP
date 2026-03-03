package ug.daes.ra.model;


import jakarta.persistence.*;

@Table(name = "subscriber_travel_history")
@Entity
public class SubscriberTravelHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "passport")
    private String passport;

    @Column(name = "travel_date")
    private String travelDate;



    @Column(name = "status")
    private String status;

    @Column(name = "immigration_status")
    private String immigrationStatus;


    @Column(name = "immigration_type")
    private String immigrationType;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImmigrationStatus() {
        return immigrationStatus;
    }

    public void setImmigrationStatus(String immigrationStatus) {
        this.immigrationStatus = immigrationStatus;
    }

    public String getImmigrationType() {
        return immigrationType;
    }

    public void setImmigrationType(String immigrationType) {
        this.immigrationType = immigrationType;
    }

    @Override
    public String toString() {
        return "SubscriberTravelHistory{" +
                "id=" + id +
                ", passport='" + passport + '\'' +
                ", travelDate=" + travelDate +
                ", status='" + status + '\'' +
                ", immigrationStatus='" + immigrationStatus + '\'' +
                ", immigrationType='" + immigrationType + '\'' +
                '}';
    }
}
