package com.mercedes.tiago.mercedesproject.dto;

import java.util.List;

public class RootDTO {

    private List<DealerDTO> dealers;

    private List<BookingDTO> bookings;

    public RootDTO() {
    }

    public List<DealerDTO> getDealers() {
        return dealers;
    }

    public void setDealers(List<DealerDTO> dealers) {
        this.dealers = dealers;
    }

    public List<BookingDTO> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingDTO> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "RootDTO{" +
                "dealers=" + dealers +
//                ", bookings=" + bookings +
                '}';
    }
}
