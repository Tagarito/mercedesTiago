package com.mercedes.tiago.mercedesproject.persistence.classes;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DEALERS")

public class Dealer {
    @Id
    @Column(name = "DEALER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String idString;

    @Column(length = 500)
    private String name;

    private double latitude;

    private double longitude;

    @ElementCollection
    @CollectionTable(name = "VEHICLES",
            joinColumns = @JoinColumn(name = "DEALER_ID",referencedColumnName = "DEALER_ID"))
    @Column(name = "VEHICLE_ID")
    private List<Long> vehicles  = new ArrayList<>();

    @ElementCollection(targetClass = String.class)
    private List<String> closed = new ArrayList<>();

    private Long createdAtMilliseconds;

    public Dealer(){}

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setVehicles(List<Long> vehicles) {
        this.vehicles = vehicles;
    }

    public void setClosed(List<String> closed) {
        this.closed = closed;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatedAtMilliseconds() {
        return createdAtMilliseconds;
    }

    public void setCreatedAtMilliseconds(Long createdAtMilliseconds) {
        this.createdAtMilliseconds = createdAtMilliseconds;
    }



    public List<Long> getVehicles() {
        return vehicles;
    }

    public List<String> getClosed() {
        return closed;
    }

    @Override
    public String toString() {
        return "Dealer{" +
                "id=" + id +
                ", idString='" + idString + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", vehicles=" + vehicles +
                ", closed=" + closed +
                ", createdAtMilliseconds=" + createdAtMilliseconds +
                '}';
    }
}
