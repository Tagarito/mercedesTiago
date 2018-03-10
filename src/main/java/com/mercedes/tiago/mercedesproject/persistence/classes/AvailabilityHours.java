package com.mercedes.tiago.mercedesproject.persistence.classes;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.List;

//Limitations of Hibernate
@Entity
@Table(name = "AVAILABILITY_HOURS")
public class AvailabilityHours {

    @Id
    @Column(name = "AVAILABILITY_HOURS_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection(targetClass = Long.class)
    private List<Long> hours;

    private String vehicleId;

    private String dayOfWeek;

    public AvailabilityHours() {
    }

    public Long getId() {
        return id;
    }


    public List<Long> getHours() {
        return hours;
    }

    public void setHours(List<Long> hours) {
        this.hours = hours;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
