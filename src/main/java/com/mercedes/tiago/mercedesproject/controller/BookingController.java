package com.mercedes.tiago.mercedesproject.controller;

import com.mercedes.tiago.mercedesproject.dto.BookingDTO;
import com.mercedes.tiago.mercedesproject.exception.*;
import com.mercedes.tiago.mercedesproject.persistence.classes.Booking;
import com.mercedes.tiago.mercedesproject.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PutMapping("/add")
    public void createBooking(@RequestBody @Valid BookingDTO bookingDTO) throws BookingAlreadyExistsException, VehicleNotFoundException {
        //No point in returning booking because it wont have collections information due to transactions stuff.
        bookingService.addBookingDTO(bookingDTO);
    }

    @GetMapping("/get/{idString}")
    public BookingDTO getBookingByName(@PathVariable(value = "idString") String idString) throws BookingNotFoundException {
        Booking booking = bookingService.getBooking(idString);
        if(booking == null){
            return null;
        }else {
            return new BookingDTO(booking);
        }
    }

    @PutMapping("/cancel/{idString}")
    public void cancelBookingByName(@PathVariable(value = "idString") String idString,
                                          @RequestBody String reason) throws ReasonDoesntExistException, BookingNotFoundException, BookingAlreadyCanceledException {
        bookingService.cancelBooking(idString, reason);
    }
}
