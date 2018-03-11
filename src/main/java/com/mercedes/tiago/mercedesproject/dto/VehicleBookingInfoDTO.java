package com.mercedes.tiago.mercedesproject.dto;

import com.mercedes.tiago.mercedesproject.persistence.classes.AvailabilityHours;
import com.mercedes.tiago.mercedesproject.persistence.classes.Vehicle;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;

public class VehicleBookingInfoDTO {

    private String id;

    private String model;

    private String fuel;

    private String transmission;

    private HashMap<String, HashMap<String, String>> availability= new HashMap<>();

    public VehicleBookingInfoDTO() {
    }

    public VehicleBookingInfoDTO(Vehicle v) {
        this.id = v.getIdString();
        this.model=v.getModel();
        this.fuel = v.getFuel();
        this.transmission = v.getTransmission();
        HashMap<String, HashMap<String, String>> copyAvailability = new HashMap<>();
        HashMap<String, String> copyListString;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HHmm");
        for(Map.Entry<String, AvailabilityHours> a: v.getAvailability().entrySet()){
            copyListString = new HashMap<>();
            for(Map.Entry<Long, String> bookedHour:a.getValue().getHours().entrySet()){
                //pass hours to string
                copyListString.put(new DateTime(bookedHour).toString(formatter), bookedHour.getValue());
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

    public HashMap<String, HashMap<String, String>> getAvailability() {
        return availability;
    }

    public void setAvailability(HashMap<String, HashMap<String, String>> availability) {
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
