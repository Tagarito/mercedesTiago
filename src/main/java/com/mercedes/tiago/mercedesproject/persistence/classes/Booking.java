package com.mercedes.tiago.mercedesproject.persistence.classes;

import javax.persistence.*;

@Entity
@Table(name = "BOOKINGS")
public class Booking {
    @Id
    @Column(name = "DEALER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String idString;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VEHICLE_ID")
    private Vehicle vehicle;

    private String firstName;

    private String lastName;

    private Long pickupDate;

    private Long createdAt;

    private Long cancelledAt;

    private String cancelledReason;

    public Booking() {
    }

    public Long getId() {
        return id;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        if(!vehicle.getBookings().contains(this)){
            vehicle.getBookings().add(this);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Long pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(Long cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getCancelledReason() {
        return cancelledReason;
    }

    public void setCancelledReason(String cancelledReason) {
        this.cancelledReason = cancelledReason;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", idString='" + idString + '\'' +
                ", vehicle='" + vehicle + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pickupDate=" + pickupDate +
                ", createdAt=" + createdAt +
                ", cancelledAt=" + cancelledAt +
                ", cancelledReason='" + cancelledReason + '\'' +
                '}';
    }
}
