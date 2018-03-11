package com.mercedes.tiago.mercedesproject.dto;


import com.mercedes.tiago.mercedesproject.persistence.classes.Booking;
import com.mercedes.tiago.mercedesproject.persistence.classes.Vehicle;
import org.joda.time.DateTime;

public class BookingDTO {

    private String id;

    private String vehicleId;

    private String firstName;

    private String lastName;

    private DateTime pickupDate;

    private DateTime createdAt;

    private DateTime cancelledAt;

    private String cancelledReason;

    public BookingDTO() {
    }

    public BookingDTO(Booking booking) {
        this.id = booking.getIdString();
        VehicleDTO vehicleDTO = new VehicleDTO(booking.getVehicle());

        this.vehicleId = vehicleDTO.getId();
        this.firstName = booking.getFirstName();
        this.lastName = booking.getLastName();
        this.pickupDate = new DateTime(booking.getPickupDate());
        this.createdAt = new DateTime(booking.getCreatedAt());
        this.cancelledAt = new DateTime(booking.getCancelledAt());
        this.cancelledReason = booking.getCancelledReason();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
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

    public DateTime getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(DateTime pickupDate) {
        this.pickupDate = pickupDate;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(DateTime cancelledAt) {
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
        return "BookingDTO{" +
                "id='" + id + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pickupDate=" + pickupDate +
                ", createdAt=" + createdAt +
                ", cancelledAt=" + cancelledAt +
                ", cancelledReason='" + cancelledReason + '\'' +
                '}';
    }
}
