package com.mercedes.tiago.mercedesproject.controller;

import com.mercedes.tiago.mercedesproject.dto.*;
import com.mercedes.tiago.mercedesproject.exception.*;
import com.mercedes.tiago.mercedesproject.persistence.classes.Dealer;
import com.mercedes.tiago.mercedesproject.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dealer")
public class DealerController {

    @Autowired
    private DealerService dealerService;

    @PutMapping("/add")
    public void addDealer(@RequestBody @Valid DealerDTO dealerDTO) throws DealerAlreadyExistsException, ClosedListContainsNonWeekDaysException, VehicleAlreadyExistsException, AvailabilityMapContainsNonWeekDaysException, AvailabilityMapContainsNonHoursException, InvalidCoordinatesException {
        //No point in returning dealer because it wont have collections information due to transactions stuff.
        dealerService.addDealerDto(dealerDTO);
    }

    @GetMapping("/get/{idString}")
    public DealerDTO getDealerByName(@PathVariable(value = "idString") String idString) throws DealerNotFoundException {
        return new DealerDTO(dealerService.getDealer(idString));
    }

    @PutMapping("/closest")
    public DealerDTO getClosestDealer(@RequestBody @Valid ClosestDealerDto closestDealerDto) throws InvalidCoordinatesException {

        Dealer dealer = dealerService.getClosestDealer(closestDealerDto);
        if(dealer == null){
            return null;
        }
        return new DealerDTO(dealer);
    }

    @PutMapping("/closestlist")
    public List<DealerDTO> getClosestDealers(
            @RequestBody @Valid ClosestDealerDto closestDealerDto) {

        List<Dealer> dealersByDistance = dealerService.getDealersSortedByDistance(closestDealerDto);
        if(dealersByDistance == null){
            return null;
        }

        List<DealerDTO> dealerDTOList = new ArrayList<>();
        for(Dealer d :dealersByDistance){
            dealerDTOList.add(new DealerDTO(d));
        }
        return dealerDTOList;
    }

    @PutMapping("/dealersinsidepolygon")
    public List<DealerDTO> dealersInsidePolygon(@RequestBody @Valid GetDealersInsidePolygonDto getDealersInsidePolygonDto){

        List<Dealer> dealersInsidePolygon =
                dealerService.getDealersInsidePolygon(getDealersInsidePolygonDto);
        List<DealerDTO> dealerDTOList = new ArrayList<>();
        for(Dealer d: dealersInsidePolygon){
            dealerDTOList.add(new DealerDTO(d));
        }
        return dealerDTOList;
    }


}
