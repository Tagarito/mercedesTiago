package com.mercedes.tiago.mercedesproject.persistence.classes;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEALER_ID")
    private Dealer dealer;

    private Long createdAtMilliseconds;

    @OneToMany(mappedBy = "vehicle")
    private List<Booking> bookings = new ArrayList<>();


    public Vehicle() {
    }

    public Long getId() {
        return id;
    }


    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
        if(!dealer.getVehicles().contains(this)){
            dealer.getVehicles().add(this);
        }
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

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking){
        this.bookings.add(booking);
        if(booking.getVehicle() != this){
            booking.setVehicle(this);
        }
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
                ", dealer=" + dealer +
                ", createdAtMilliseconds=" + createdAtMilliseconds +
                '}';
    }
}
