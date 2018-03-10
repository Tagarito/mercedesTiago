package com.mercedes.tiago.mercedesproject.service;

import com.mercedes.tiago.mercedesproject.dto.DealerDTO;
import com.mercedes.tiago.mercedesproject.dto.VehicleDTO;
import com.mercedes.tiago.mercedesproject.exception.*;
import com.mercedes.tiago.mercedesproject.persistence.classes.Dealer;
import com.mercedes.tiago.mercedesproject.persistence.classes.Vehicle;
import com.mercedes.tiago.mercedesproject.persistence.repository.DealerRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;


@Service
public class DealerService {

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private VehicleService vehicleService;


    //Returns empty vehicles on add. This is probably due to JPA commits.
    public Dealer addDealer(String idString,
                            String name,
                            double latitude,
                            double longitude,
                            List<VehicleDTO> vehicleDTOList,
                            List<String> closedDto) throws ClosedListContainsNonWeekDaysException, DealerAlreadyExistsException, AvailabilityMapContainsNonHoursException, VehicleAlreadyExistsException, AvailabilityMapContainsNonWeekDaysException {

        Dealer dealerCheck = null;
        try {
            dealerCheck = this.getDealer(idString);
        } catch (DealerNotFoundException de) {
            Dealer dealer = new Dealer();
            dealer.setIdString(idString);
            dealer.setName(name);
            dealer.setLatitude(latitude);
            dealer.setLongitude(longitude);

            List<String> closed = new ArrayList<>();
            for(String s: closedDto){
                String toAdd = s.toUpperCase();

                try {
                    DayOfWeek.valueOf(toAdd);
                }catch (IllegalArgumentException e){
                    throw new ClosedListContainsNonWeekDaysException();
                }
                closed.add(toAdd);
            }
            dealer.setClosed(closed);
            dealer.setCreatedAtMilliseconds(new DateTime().getMillis());
            List<String> toDeleteNames = new ArrayList<>();
            Vehicle vehicle;
            try {
                for (VehicleDTO v : vehicleDTOList) {
                    vehicle = vehicleService.addVehicle(v, dealer.getId());
                    toDeleteNames.add(vehicle.getIdString());
                }
            } catch (Exception e) {
                vehicleService.deleteVehicles(toDeleteNames);
                throw e;
            }
            dealer = dealerRepository.save(dealer);
            return dealer;
        }
            throw new DealerAlreadyExistsException();


    }

    public Dealer addDealerDto(DealerDTO dealerDTO) throws DealerAlreadyExistsException, ClosedListContainsNonWeekDaysException, VehicleAlreadyExistsException, AvailabilityMapContainsNonWeekDaysException, AvailabilityMapContainsNonHoursException {
        return this.addDealer(
                dealerDTO.getId(),
                dealerDTO.getName(),
                dealerDTO.getLatitude(),
                dealerDTO.getLongitude(),
                dealerDTO.getVehicles(),
                dealerDTO.getClosed());
    }

    public Dealer getDealer(String id) throws DealerNotFoundException {
        Dealer dealer = dealerRepository.findByidString(id);
        if(dealer == null){
            throw new DealerNotFoundException();
        }
        return dealer;
    }

}
