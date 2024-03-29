package com.mercedes.tiago.mercedesproject.dto;

import com.mercedes.tiago.mercedesproject.persistence.classes.AvailabilityHours;
import com.mercedes.tiago.mercedesproject.persistence.classes.Vehicle;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleDTO {

    private String id;

    private String model;

    private String fuel;

    private String transmission;

    private HashMap<String, List<String>> availability= new HashMap<>();

    public VehicleDTO() {
    }

    public VehicleDTO(Vehicle v) {
        this.id = v.getIdString();
        this.model=v.getModel();
        this.fuel = v.getFuel();
        this.transmission = v.getTransmission();
        HashMap<String, List<String>> copyAvailability = new HashMap<>();
        List<String> copyListString;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HHmm");
        for(Map.Entry<String, AvailabilityHours> a: v.getAvailability().entrySet()){
            copyListString = new ArrayList<>();
            for(Map.Entry<Long, String> l:a.getValue().getHours().entrySet()){
                //pass hours to string
                copyListString.add(new DateTime(l.getKey()).toString(formatter));
            }
            copyAvailability.put(a.getKey(), copyListString);
        }
        this.availability = copyAvailability;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public HashMap<String, List<String>> getAvailability() {
        return availability;
    }

    public void setAvailability(HashMap<String, List<String>> availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", fuel='" + fuel + '\'' +
                ", transmission='" + transmission + '\'' +
                ", availability=" + availability +
                '}';
    }
}
