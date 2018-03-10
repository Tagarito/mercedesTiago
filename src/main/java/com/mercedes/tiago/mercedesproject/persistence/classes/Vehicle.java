package com.mercedes.tiago.mercedesproject.persistence.classes;

import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "VEHICLES")

public class Vehicle {
    @Id
    @Column(name = "VEHICLE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String idString;

    private String model;

    private String fuel;

    private String transmission;

    @ElementCollection
    private Map<String, AvailabilityHours> availability;

    @Column(name = "DEALER_ID")
    private Long dealerId;

    private Long createdAtMilliseconds;


    public Vehicle() {
    }

    public Long getId() {
        return id;
    }


    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatedAtMilliseconds() {
        return createdAtMilliseconds;
    }

    public void setCreatedAtMilliseconds(Long createdAtMilliseconds) {
        this.createdAtMilliseconds = createdAtMilliseconds;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public Map<String, AvailabilityHours> getAvailability() {
        return availability;
    }

    public void setAvailability(Map<String, AvailabilityHours> availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", idString='" + idString + '\'' +
                ", model='" + model + '\'' +
                ", fuel='" + fuel + '\'' +
                ", transmission='" + transmission + '\'' +
                ", availability=" + availability +
                ", dealerId=" + dealerId +
                ", createdAtMilliseconds=" + createdAtMilliseconds +
                '}';
    }
}
