package com.mercedes.tiago.mercedesproject.service;

import com.mercedes.tiago.mercedesproject.dto.VehicleDTO;
import com.mercedes.tiago.mercedesproject.exception.*;
import com.mercedes.tiago.mercedesproject.persistence.classes.AvailabilityHours;
import com.mercedes.tiago.mercedesproject.persistence.classes.Booking;
import com.mercedes.tiago.mercedesproject.persistence.classes.Dealer;
import com.mercedes.tiago.mercedesproject.persistence.classes.Vehicle;
import com.mercedes.tiago.mercedesproject.persistence.repository.AvailabilityHoursRepository;
import com.mercedes.tiago.mercedesproject.persistence.repository.VehicleRepository;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AvailabilityHoursRepository availabilityHoursRepository;



    public Vehicle addVehicle(VehicleDTO vehicleDTO, Dealer dealer) throws VehicleAlreadyExistsException, AvailabilityMapContainsNonWeekDaysException, AvailabilityMapContainsNonHoursException {
        return this.addVehicle(dealer,
                vehicleDTO.getId(),
                vehicleDTO.getModel(),
                vehicleDTO.getFuel(),
                vehicleDTO.getTransmission(),
                vehicleDTO.getAvailability()
        );
    }

    public Vehicle addVehicle(Dealer dealer,
                               String idString,
                               String model,
                               String fuel,
                               String transmission,
                               HashMap<String, List<String>> availabilityDto) throws VehicleAlreadyExistsException, AvailabilityMapContainsNonWeekDaysException, AvailabilityMapContainsNonHoursException {

        Vehicle vehicleCheck = null;
        try {
            vehicleCheck = this.getVehicle(idString);
        } catch (VehicleNotFoundException ve) {
            Vehicle vehicle = new Vehicle();
            vehicle.setDealer(dealer);
            vehicle.setIdString(idString);
            vehicle.setModel(model);
            vehicle.setFuel(fuel);
            vehicle.setTransmission(transmission);
            vehicle.setCreatedAtMilliseconds(new DateTime().getMillis());

            //PARSE DTO TO Map<String, HashMap<Long, Boolean>>
            Map<String, HashMap<Long, String>> availability = new HashMap<>();
            for (Map.Entry<String, List<String>> a : availabilityDto.entrySet()) {
                String toAdd = a.getKey().toUpperCase();

                try {
                    DayOfWeek.valueOf(toAdd);
                } catch (IllegalArgumentException e) {
                    throw new AvailabilityMapContainsNonWeekDaysException();
                }

                HashMap<Long, String> availabilityHoursLong = new HashMap<>();
                DateTimeFormatter formatter = DateTimeFormat.forPattern("HHmm");
                DateTime dt;
                for (String s : a.getValue()) {
                    dt = formatter.parseDateTime(s);

                    //"" represents noone has reserved this hour slot. null was bringing aditional JPA problems.
                    availabilityHoursLong.put(dt.getMillis(), "");
                }

                availability.put(toAdd, availabilityHoursLong);

            }

            //TRANSFORM HashMap<Long, Boolean> in AvailabilityHours because of JPA
            Map<String, AvailabilityHours> availabilitySave = new HashMap<>();
            for (Map.Entry<String, HashMap<Long, String>> a : availability.entrySet()) {
                AvailabilityHours availabilityHours = new AvailabilityHours();
                availabilityHours.setVehicleId(idString);

                //Used to be able to cancel bookings
                //Every hour starts bookable on a vehicle
                HashMap<Long, String> availabiltyHashMap = new HashMap<>();
                for(Map.Entry<Long, String> l : a.getValue().entrySet()){
                    availabiltyHashMap.put(l.getKey(), l.getValue());
                }
                availabilityHours.setHours(availabiltyHashMap);
                availabilityHours.setDayOfWeek(a.getKey());
                availabilityHours = availabilityHoursRepository.save(availabilityHours);
                availabilitySave.put(a.getKey(), availabilityHours);
            }
            vehicle.setAvailability(availabilitySave);
            vehicleRepository.save(vehicle);
            return vehicle;
        }
        throw new VehicleAlreadyExistsException();
    }

    @Transactional
    public void deleteVehicles(List<String> toDelete) {
        for(String s: toDelete){
            Vehicle vehicle = vehicleRepository.findByidString(s);
            for(Map.Entry<String, AvailabilityHours> a:vehicle.getAvailability().entrySet()){
                AvailabilityHours availabilityHours = a.getValue();
                availabilityHoursRepository.delete(availabilityHours);
            }
            vehicleRepository.delete(vehicle);
        }
    }

    public Vehicle getVehicle(String id) throws VehicleNotFoundException {
        Vehicle vehicle = vehicleRepository.findByidString(id);
        if(vehicle == null){
            throw new VehicleNotFoundException();
        }
        return vehicle;
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public AvailabilityHours saveAvailabilityHours(AvailabilityHours availabilityHours){
        return availabilityHoursRepository.save(availabilityHours);
    }

    public void setBooking(String vehicleId, Booking booking) throws VehicleNotFoundException {
        Vehicle vehicle = this.getVehicle(vehicleId);
        vehicle.addBooking(booking);
        vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getVehiclesByModel(String vehicleModel) {
        return vehicleRepository.getVehiclesByModel(vehicleModel);
    }

    public List<Vehicle> getVehiclesByFuel(String vehicleFuel) {
        return vehicleRepository.getVehiclesByFuel(vehicleFuel);
    }

    public List<Vehicle> getVehiclesByTransmission(String vehicleTransmission) {
        return vehicleRepository.getVehiclesByTransmission(vehicleTransmission);
    }

    public List<Vehicle> getVehiclesByDealer(String dealerName) {
        return vehicleRepository.getVehiclesByDealer(dealerName);
    }

    //This could be easily extended to only search for part of the attributes.
    public List<Dealer> getVehiclesByAttributes(String model, String fuel, String transmission) {
        return vehicleRepository.getVehiclesWithAttributes(model, fuel, transmission);
    }
}
