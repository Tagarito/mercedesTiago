package com.mercedes.tiago.mercedesproject.dto;

import com.mercedes.tiago.mercedesproject.persistence.classes.Dealer;
import com.mercedes.tiago.mercedesproject.persistence.classes.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class DealerDTO {

    private String id;

    private String name;

    private double latitude;

    private double longitude;

    private List<VehicleDTO> vehicles = new ArrayList<>();

    private List<String> closed= new ArrayList<>();

    public DealerDTO() {
    }

    public DealerDTO(Dealer dealer) {
        this.id = dealer.getIdString();
        this.name=dealer.getName();
        this.latitude=dealer.getLatitude();
        this.longitude =dealer.getLongitude();
        for(Vehicle v: dealer.getVehicles()){
            vehicles.add(new VehicleDTO(v));
        }
        for(String s:dealer.getClosed()){
            closed.add(new String(s).toLowerCase());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<VehicleDTO> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleDTO> vehicles) {
        this.vehicles = vehicles;
    }

    public List<String> getClosed() {
        return closed;
    }

    public void setClosed(List<String> closed) {
        this.closed = closed;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "DealerDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", vehicles=" + vehicles +
                ", closed=" + closed +
                '}';
    }
}
