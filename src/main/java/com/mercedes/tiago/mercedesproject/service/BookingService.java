package com.mercedes.tiago.mercedesproject.service;

import com.mercedes.tiago.mercedesproject.dto.BookingDTO;
import com.mercedes.tiago.mercedesproject.exception.BookingAlreadyExistsException;
import com.mercedes.tiago.mercedesproject.exception.BookingNotFoundException;
import com.mercedes.tiago.mercedesproject.exception.ReasonDoesntExistException;
import com.mercedes.tiago.mercedesproject.exception.VehicleNotFoundException;
import com.mercedes.tiago.mercedesproject.persistence.classes.AvailabilityHours;
import com.mercedes.tiago.mercedesproject.persistence.classes.Booking;
import com.mercedes.tiago.mercedesproject.persistence.classes.Vehicle;
import com.mercedes.tiago.mercedesproject.persistence.repository.BookingRepository;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.omg.SendingContext.RunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private VehicleService vehicleService;

    public static int invalidDay = 0;
    public static int possibleHours = 0;
    public static int invalidHours = 0;
    public static int duplicateTimes = 0;


    public Booking addBookingDTO(BookingDTO bookingDTO) throws BookingAlreadyExistsException, VehicleNotFoundException {
        return this.addBooking(
                bookingDTO.getId(),
                bookingDTO.getVehicleId(),
                bookingDTO.getFirstName(),
                bookingDTO.getLastName(),
                bookingDTO.getPickupDate(),
                bookingDTO.getCreatedAt(),
                bookingDTO.getCancelledAt(),
                bookingDTO.getCancelledReason());

    }


    //Once a booking is done it stays forever until it gets canceled.
    //Booking doesn't take into account date. I would be able to do this if
    //AvailabilityHours: Map<Long, Boolean> hours was of type Map<Long, List<Long>> hours;
    //But for this I would have to create another intermediate classe because of JPA limitations
    //as fas as I know.

    private Booking addBooking(String idString,
                               String vehicleId,
                               String firstName,
                               String lastName,
                               DateTime pickupDate,
                               DateTime createdAt,
                               DateTime cancelledAt,
                               String cancelledReason) throws BookingAlreadyExistsException, VehicleNotFoundException {

        Booking dealerCheck = null;
        try {
            dealerCheck = this.getBooking(idString);
        } catch (BookingNotFoundException be) {
            try {


                DateTimeFormatter formatter = DateTimeFormat.forPattern("HHmm");


                Booking booking = new Booking();
                booking.setIdString(idString);
                vehicleService.setBooking(vehicleId, booking);
                booking.setFirstName(firstName);
                booking.setLastName(lastName);
                booking.setCancelledAt(((cancelledAt == null) ? null : cancelledAt.getMillis()));
                booking.setCancelledReason(cancelledReason);
                booking.setCreatedAt(((createdAt == null) ? null : createdAt.getMillis()));
                booking.setPickupDate(((pickupDate == null) ? null : pickupDate.getMillis()));

                if (pickupDate != null) {

                    System.out.println(pickupDate);

                    Vehicle vehicle = vehicleService.getVehicle(vehicleId);
                    DayOfWeek dow = DayOfWeek.of(pickupDate.getDayOfWeek());
                    Map<String, AvailabilityHours> availability = vehicle.getAvailability();
                    if (!availability.containsKey(dow.toString())) {
                        invalidDay++;
                        cancelBooking(booking, "Vehicle is not available at this day of the week");
                    } else {
                        AvailabilityHours availableHours = availability.get(dow.toString());
                        Map<Long, String> availableHoursLong = availableHours.getHours();
                        Long pickupTimeConverted =
                                formatter.parseDateTime(pickupDate.toString(formatter)).getMillis();

                        if (availableHoursLong.containsKey(pickupTimeConverted)) {
                            possibleHours++;
                            //Compare to "" because it's the idString of the booking, so we can keep track of who booked each hour
                            //Could be of type Booking but it would require more work than doing a get request by idString to the booking
                            //controller
                            if (availableHoursLong.get(pickupTimeConverted).equals("")) {
                                //Guarantee that the value was already there as the default value (in this case true)
                                if (availableHoursLong.put(pickupTimeConverted, booking.getIdString()) != null) {
                                    vehicleService.saveAvailabilityHours(availableHours);
                                }
                            } else {
                                duplicateTimes++;
                                cancelBooking(booking, "Hour and day already booked for this vehicle");
                            }

                        } else {
                            invalidHours++;
                            cancelBooking(booking, "Vehicle is not available at this hour of the day");

                        }

                    }
                }


                booking = bookingRepository.save(booking);
                return booking;

            }catch (ReasonDoesntExistException e) {
                throw new RuntimeException("Programmer should provide a reason for booking canceling on processing of starter file.");
            }
        }
        throw new BookingAlreadyExistsException();

    }

    public Booking cancelBooking(String idString, String reason) throws BookingNotFoundException, ReasonDoesntExistException {
        Booking booking = this.getBooking(idString);
        booking.setCancelledAt(DateTime.now().getMillis());
        if(reason == null || reason.isEmpty()){
            throw new ReasonDoesntExistException();
        }
        booking.setCancelledReason(reason);
        booking = bookingRepository.save(booking);
        return booking;
    }

    public Booking cancelBooking(Booking booking, String reason) throws ReasonDoesntExistException {
        booking.setCancelledAt(DateTime.now().getMillis());
        if(reason == null || reason.isEmpty()){
            throw new ReasonDoesntExistException();
        }
        booking.setCancelledReason(reason);
        return booking;
    }

    public Booking getBooking(String id) throws BookingNotFoundException {
        Booking booking = bookingRepository.findByidString(id);
        if(booking == null){
            throw new BookingNotFoundException();
        }
        return booking;
    }

    public List<Booking> addBookings(List<BookingDTO> bookings) throws VehicleNotFoundException, BookingAlreadyExistsException {
        //Order by booking creation date on initial file startup. This is because if there are two bookings for
        //the same day at the same time we want to exclude the one that was made later on. New requests will be
        //more recent.
        bookings.sort(new Comparator<BookingDTO>() {
            @Override
            public int compare(BookingDTO o1, BookingDTO o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });
       List<Booking> bookingAfter = new ArrayList<>();
//        int bookingsRepeated = 0;
//        int vehiclesNotFound = 0;
//        List<String> repeatedBookings = new ArrayList<>();
        for(BookingDTO bookingDTO:bookings){
            try {
                bookingAfter.add(this.addBookingDTO(bookingDTO));
            } catch (BookingAlreadyExistsException e) {
//                bookingsRepeated++;
//                repeatedBookings.add(bookingDTO.getId());
                throw e;
            } catch (VehicleNotFoundException e) {
//                vehiclesNotFound++;
                throw e;
            }
        }
//        System.out.println(bookingsRepeated);
//        System.out.println(vehiclesNotFound);
//        for(String s: repeatedBookings){
//            System.out.println(s);
//        }
//        System.out.println("Repeated Ids: "+repeatedBookings.size());
        return bookingAfter;
    }

}
