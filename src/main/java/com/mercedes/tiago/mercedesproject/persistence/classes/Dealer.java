package com.mercedes.tiago.mercedesproject.persistence.classes;

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

    @OneToMany(mappedBy = "dealer")
    private List<Vehicle> vehicles = new ArrayList<>();

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


    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<String> getClosed() {
        return closed;
    }

    public void addVehicle(Vehicle vehicle){
        this.vehicles.add(vehicle);
        if(vehicle.getDealer() != this){
            vehicle.setDealer(this);
        }
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
