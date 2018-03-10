package com.mercedes.tiago.mercedesproject.dto;

import java.util.List;

public class DealerDTO {

    private String id;

    private String name;

    private double latitude;

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    private double longitude;

    private List<VehicleDTO> vehicles;

    private List<String> closed;

    public DealerDTO() {
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
