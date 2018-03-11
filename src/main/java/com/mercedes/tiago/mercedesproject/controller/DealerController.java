package com.mercedes.tiago.mercedesproject.controller;

import com.mercedes.tiago.mercedesproject.dto.ClosestDealerDto;
import com.mercedes.tiago.mercedesproject.dto.DealerDTO;
import com.mercedes.tiago.mercedesproject.dto.VehicleDTO;
import com.mercedes.tiago.mercedesproject.exception.*;
import com.mercedes.tiago.mercedesproject.persistence.classes.Dealer;
import com.mercedes.tiago.mercedesproject.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/dealer")
public class DealerController {

    @Autowired
    private DealerService dealerService;

    @PutMapping("/add")
    public void addDealer(@RequestBody @Valid DealerDTO dealerDTO) throws DealerAlreadyExistsException, ClosedListContainsNonWeekDaysException, VehicleAlreadyExistsException, AvailabilityMapContainsNonWeekDaysException, AvailabilityMapContainsNonHoursException {
        //No point in returning dealer because it wont have collections information due to transactions stuff.
        dealerService.addDealerDto(dealerDTO);
    }

    @GetMapping("/get/{idString}")
    public DealerDTO getDealerByName(@PathVariable(value = "idString") String idString) throws DealerNotFoundException {
        return new DealerDTO(dealerService.getDealer(idString));
    }

    @GetMapping("/closest/{latitude}/{longitude}/{model}/{fuel}/{transmission}")
//    public DealerDTO getClosestDealer(@RequestBody @Valid ClosestDealerDto closestDealerDto){
    public DealerDTO getClosestDealer(
            @PathVariable(value = "latitude") double latitude,
            @PathVariable(value = "longitude") double longitude,
            @PathVariable(value = "model") String model,
            @PathVariable(value = "fuel") String fuel,
            @PathVariable(value = "transmission") String transmission){

        ClosestDealerDto closestDealerDto = new ClosestDealerDto();
        closestDealerDto.setLatitude(latitude);
        closestDealerDto.setLongitude(longitude);
        closestDealerDto.setModel(model);
        closestDealerDto.setFuel(fuel);
        closestDealerDto.setTransmission(transmission);
        Dealer dealer = dealerService.getClosestDealer(closestDealerDto);
        if(dealer == null){
            return null;
        }
        return new DealerDTO(dealer);
    }


}
