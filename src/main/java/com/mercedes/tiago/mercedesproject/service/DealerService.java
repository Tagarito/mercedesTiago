package com.mercedes.tiago.mercedesproject.service;

import com.mercedes.tiago.mercedesproject.dto.ClosestDealerDto;
import com.mercedes.tiago.mercedesproject.dto.DealerDTO;
import com.mercedes.tiago.mercedesproject.dto.VehicleDTO;
import com.mercedes.tiago.mercedesproject.exception.*;
import com.mercedes.tiago.mercedesproject.persistence.classes.Dealer;
import com.mercedes.tiago.mercedesproject.persistence.classes.DistanceCalculator;
import com.mercedes.tiago.mercedesproject.persistence.classes.Vehicle;
import com.mercedes.tiago.mercedesproject.persistence.repository.DealerRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class DealerService {

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private VehicleService vehicleService;

    private final String unit = "K"; //K, M, N(Nautical Miles)



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
            dealer = dealerRepository.save(dealer);
            List<String> toDeleteNames = new ArrayList<>();
            Vehicle vehicle;
            try {
                for (VehicleDTO v : vehicleDTOList) {
                    vehicle = vehicleService.addVehicle(v, dealer);
                    toDeleteNames.add(vehicle.getIdString());
                }
            } catch (Exception e) {
                vehicleService.deleteVehicles(toDeleteNames);
                dealerRepository.delete(dealer);
                throw e;
            }
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

    public Dealer getDealer(String idString) throws DealerNotFoundException {
        Dealer dealer = dealerRepository.findByidString(idString);
        if(dealer == null){
            throw new DealerNotFoundException();
        }
        return dealer;
    }

    public List<Dealer> addDealers(List<DealerDTO> dealers) throws VehicleAlreadyExistsException, AvailabilityMapContainsNonWeekDaysException, ClosedListContainsNonWeekDaysException, AvailabilityMapContainsNonHoursException, DealerAlreadyExistsException {
        List<Dealer> dealersAfter = new ArrayList<>();
        for(DealerDTO dealerDTO:dealers){
            dealersAfter.add(this.addDealerDto(dealerDTO));
        }
        return dealersAfter;
    }

    public Dealer getClosestDealer(@Valid ClosestDealerDto closestDealerDto) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByAttributes(
                closestDealerDto.getModel(),
                closestDealerDto.getFuel(),
                closestDealerDto.getTransmission());

        if(vehicles.size() == 0){
            return null;
        }
        Dealer bestDealer= vehicles.get(0).getDealer();
        System.out.println(bestDealer.getName());
        System.out.println(bestDealer.getLatitude());
        System.out.println(bestDealer.getLongitude());
        double bestDistance = DistanceCalculator.distance(
                closestDealerDto.getLatitude(),
                closestDealerDto.getLongitude(),
                bestDealer.getLatitude(),
                bestDealer.getLongitude(), unit);
        double newDistance;
        for (int i = 1; i < vehicles.size(); i++) {
            System.out.println(vehicles.get(i).getDealer().getName());
            System.out.println(vehicles.get(i).getDealer().getLatitude());
            System.out.println(vehicles.get(i).getDealer().getLongitude());
            newDistance = DistanceCalculator.distance(
                    closestDealerDto.getLatitude(),
                    closestDealerDto.getLongitude(),
                    bestDealer.getLatitude(),
                    bestDealer.getLongitude(), unit);
            if(newDistance < bestDistance){
                bestDistance = newDistance;
                bestDealer = vehicles.get(i).getDealer();
            }else if(newDistance == bestDistance){
                //Because max value is exclusive I add +1.
                //Create fairness in closeness if same distance.
                int randomNum = ThreadLocalRandom.current().nextInt(0, 1+1);
                if(randomNum == 0){
                    continue;
                }else {
                    bestDistance = newDistance;
                    bestDealer = vehicles.get(i).getDealer();
                }
            }
        }
        return bestDealer;

    }
}
