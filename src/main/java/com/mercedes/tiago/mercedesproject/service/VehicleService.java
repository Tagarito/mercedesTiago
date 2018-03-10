package com.mercedes.tiago.mercedesproject.service;

import com.mercedes.tiago.mercedesproject.dto.VehicleDTO;
import com.mercedes.tiago.mercedesproject.exception.AvailabilityMapContainsNonHoursException;
import com.mercedes.tiago.mercedesproject.exception.AvailabilityMapContainsNonWeekDaysException;
import com.mercedes.tiago.mercedesproject.exception.ClosedListContainsNonWeekDaysException;
import com.mercedes.tiago.mercedesproject.exception.VehicleAlreadyExistsException;
import com.mercedes.tiago.mercedesproject.persistence.classes.AvailabilityHours;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AvailabilityHoursRepository availabilityHoursRepository;


    public Vehicle addVehicle(VehicleDTO vehicleDTO, Long id) throws VehicleAlreadyExistsException, AvailabilityMapContainsNonWeekDaysException, AvailabilityMapContainsNonHoursException {
        return this.addVehicle(id,
                vehicleDTO.getId(),
                vehicleDTO.getModel(),
                vehicleDTO.getFuel(),
                vehicleDTO.getTransmission(),
                vehicleDTO.getAvailability()
        );
    }

    private Vehicle addVehicle(Long dealerId, String id,
                               String model,
                               String fuel,
                               String transmission,
                               HashMap<String, List<String>> availabilityDto) throws VehicleAlreadyExistsException, AvailabilityMapContainsNonWeekDaysException, AvailabilityMapContainsNonHoursException {

        Vehicle vehicleCheck = vehicleRepository.findByidString(id);
        if(vehicleCheck != null){
            throw new VehicleAlreadyExistsException();
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setDealerId(dealerId);
        vehicle.setIdString(id);
        vehicle.setModel(model);
        vehicle.setFuel(fuel);
        vehicle.setTransmission(transmission);
        Map<String, List<Long>> availability = new HashMap<>();
        for(Map.Entry<String, List<String>> a: availabilityDto.entrySet()){
            String toAdd = a.getKey().toUpperCase();

            try {
                DayOfWeek.valueOf(toAdd);
            }catch (IllegalArgumentException e){
                throw new AvailabilityMapContainsNonWeekDaysException();
            }

            List<Long> availabilityHoursLong = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("HHmm");
            DateTime dt;
            for(String s: a.getValue()){
                dt = formatter.parseDateTime(s);
                availabilityHoursLong.add(dt.getMillis());
            }

            availability.put(toAdd, availabilityHoursLong);

        }
        Map<String, AvailabilityHours> availabilitySave = new HashMap<>();
        for(Map.Entry<String, List<Long>> a: availability.entrySet()){
            AvailabilityHours availabilityHours = new AvailabilityHours();
            availabilityHours.setVehicleId(id);
            availabilityHours.setHours(a.getValue());
            availabilityHours.setDayOfWeek(a.getKey());
            availabilityHours = availabilityHoursRepository.save(availabilityHours);
            availabilitySave.put(a.getKey(), availabilityHours);
        }
        vehicle.setAvailability(availabilitySave);
        vehicleRepository.save(vehicle);
        return vehicle;
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
}
