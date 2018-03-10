package com.mercedes.tiago.mercedesproject.controller;

import com.mercedes.tiago.mercedesproject.dto.DealerDTO;
import com.mercedes.tiago.mercedesproject.dto.VehicleDTO;
import com.mercedes.tiago.mercedesproject.exception.*;
import com.mercedes.tiago.mercedesproject.persistence.classes.Dealer;
import com.mercedes.tiago.mercedesproject.persistence.classes.Vehicle;
import com.mercedes.tiago.mercedesproject.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/dealer")
public class DealerController {

    @Autowired
    private DealerService dealerService;

    @GetMapping("/add")
    public void addDealer() throws DealerAlreadyExistsException, ClosedListContainsNonWeekDaysException, VehicleAlreadyExistsException, AvailabilityMapContainsNonWeekDaysException, AvailabilityMapContainsNonHoursException {
        DealerDTO dealerDTO = new DealerDTO();
        dealerDTO.setId("ab49f56a-451d-4721-8319-efdca5e69680");
        dealerDTO.setName("MB Albufeira");
        dealerDTO.setLatitude(37.104404);
        dealerDTO.setLongitude(-8.236308);
        List<String> closed = new ArrayList<>();
        closed.add("tuesday");
        closed.add("wednesday");
        dealerDTO.setClosed(closed);
        List<VehicleDTO> vehicles = new ArrayList<>();
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId("3928f780-295b-4dd0-8cc9-28c0738398d9");
        vehicleDTO.setModel("AMG");
        vehicleDTO.setFuel("ELECTRIC");
        vehicleDTO.setTransmission("AUTO");
        HashMap<String, List<String>> availability = new HashMap<>();
        List<String> times = new ArrayList<>();
        times.add("1000");
        times.add("1030");
        availability.put("thursday", times);
        vehicleDTO.setAvailability(availability);
        vehicles.add(vehicleDTO);
        vehicles.add(vehicleDTO);
        dealerDTO.setVehicles(vehicles);
        Dealer dealer = dealerService.addDealerDto(dealerDTO);
        System.out.println(dealer);
        try {
            System.out.println(dealer.getVehicles());
            System.out.println(dealerService.getDealer(dealer.getIdString()));
        } catch (DealerNotFoundException e) {
            e.printStackTrace();
        }
//        dealerService.addDealer("2-3asf-f23fsd","Ola");
    }

    @GetMapping("/get/{name}")
    public Dealer getDealerByName(@PathVariable(value = "name") String dealerName) throws DealerNotFoundException {
        return dealerService.getDealer(dealerName);
    }
}
