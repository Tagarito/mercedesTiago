package com.mercedes.tiago.mercedesproject.controller;

import com.mercedes.tiago.mercedesproject.dto.VehicleDTO;
import com.mercedes.tiago.mercedesproject.persistence.classes.Vehicle;
import com.mercedes.tiago.mercedesproject.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/model/{model}")
    public List<VehicleDTO> getVehiclesByModel(@PathVariable(value = "model") String vehicleModel){

        return transformListVehicleToDto(vehicleService.getVehiclesByModel(vehicleModel));

    }

    @GetMapping("/fuel/{fuel}")
    public List<VehicleDTO> getVehiclesByFuel(@PathVariable(value = "fuel") String vehicleFuel){

        return transformListVehicleToDto(vehicleService.getVehiclesByFuel(vehicleFuel));

    }

    @GetMapping("/transmission/{transmission}")
    public List<VehicleDTO> getVehiclesByTransmission(@PathVariable(value = "transmission") String vehicleTransmission){

        return transformListVehicleToDto(vehicleService.getVehiclesByTransmission(vehicleTransmission));

    }

    @GetMapping("/dealer/{dealer}")
    public List<VehicleDTO> getVehiclesByDealer(@PathVariable(value = "dealer") String dealerName){

        return transformListVehicleToDto(vehicleService.getVehiclesByDealer(dealerName));

    }

    private List<VehicleDTO> transformListVehicleToDto(List<Vehicle> vehicles){
        List<VehicleDTO> vehicleDTOS = new ArrayList<>();
        for(Vehicle v :vehicles){
            vehicleDTOS.add(new VehicleDTO(v));
        }
        return vehicleDTOS;
    }
}
