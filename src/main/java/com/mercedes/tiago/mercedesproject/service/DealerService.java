package com.mercedes.tiago.mercedesproject.service;

import com.mercedes.tiago.mercedesproject.dto.*;
import com.mercedes.tiago.mercedesproject.exception.*;
import com.mercedes.tiago.mercedesproject.persistence.classes.Dealer;
import com.mercedes.tiago.mercedesproject.persistence.classes.DistanceCalculator;
import com.mercedes.tiago.mercedesproject.persistence.classes.Vehicle;
import com.mercedes.tiago.mercedesproject.persistence.repository.DealerRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.awt.geom.Path2D;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
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
                            List<String> closedDto) throws ClosedListContainsNonWeekDaysException, DealerAlreadyExistsException, AvailabilityMapContainsNonHoursException, VehicleAlreadyExistsException, AvailabilityMapContainsNonWeekDaysException, InvalidCoordinatesException {

        Dealer dealerCheck = null;
        try {
            dealerCheck = this.getDealer(idString);
        } catch (DealerNotFoundException de) {
            Dealer dealer = new Dealer();
            dealer.setIdString(idString);
            dealer.setName(name);
            dealer.setLatitude(latitude);
            dealer.setLongitude(longitude);
            //Check if coordinates are valid
            distanceBetweenDealerAndVehicle(0,0, dealer);


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

    public Dealer addDealerDto(DealerDTO dealerDTO) throws DealerAlreadyExistsException, ClosedListContainsNonWeekDaysException, VehicleAlreadyExistsException, AvailabilityMapContainsNonWeekDaysException, AvailabilityMapContainsNonHoursException, InvalidCoordinatesException {
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

    public List<Dealer> addDealers(List<DealerDTO> dealers) throws VehicleAlreadyExistsException, AvailabilityMapContainsNonWeekDaysException, ClosedListContainsNonWeekDaysException, AvailabilityMapContainsNonHoursException, DealerAlreadyExistsException, InvalidCoordinatesException {
        List<Dealer> dealersAfter = new ArrayList<>();
        for(DealerDTO dealerDTO:dealers){
            dealersAfter.add(this.addDealerDto(dealerDTO));
        }
        return dealersAfter;
    }

    public Dealer getClosestDealer(@Valid ClosestDealerDto closestDealerDto) throws InvalidCoordinatesException {
        List<Dealer> dealers = vehicleService.getVehiclesByAttributes(
                closestDealerDto.getModel(),
                closestDealerDto.getFuel(),
                closestDealerDto.getTransmission());

        if(dealers.size() == 0){
            return null;
        }
        double latitudeGiven = closestDealerDto.getLatitude();
        double longitudeGiven = closestDealerDto.getLongitude();
        Dealer bestDealer= dealers.get(0);
        double bestDistance = distanceBetweenDealerAndVehicle(
                latitudeGiven,
                longitudeGiven,
                bestDealer);
        double newDistance;
        for (int i = 1; i < dealers.size(); i++) {
            newDistance = distanceBetweenDealerAndVehicle(
                    latitudeGiven,
                    longitudeGiven,
                    dealers.get(i));
            if(newDistance < bestDistance){
                bestDistance = newDistance;
                bestDealer = dealers.get(i);
            }else if(newDistance == bestDistance){
                //Because max value is exclusive I add +1.
                //Create fairness in closeness if same distance.
                int randomNum = ThreadLocalRandom.current().nextInt(0, 1+1);
                if(randomNum == 0){
                    continue;
                }else {
                    bestDistance = newDistance;
                    bestDealer = dealers.get(i);
                }
            }
        }
        return bestDealer;

    }

    private double distanceBetweenDealerAndVehicle(double latitude, double longitude, Dealer d) throws InvalidCoordinatesException {
        return  DistanceCalculator.distance(
                latitude,
                longitude,
                d.getLatitude(),
                d.getLongitude(), unit);
    }

    public List<Dealer> getDealersSortedByDistance(@Valid ClosestDealerDto closestDealerDto) {
        List<Dealer> dealers = vehicleService.getVehiclesByAttributes(
                closestDealerDto.getModel(),
                closestDealerDto.getFuel(),
                closestDealerDto.getTransmission());

        if(dealers.size() == 0){
            return null;
        }

        double givenLatitude = closestDealerDto.getLatitude();
        double givenLongitude = closestDealerDto.getLongitude();


        dealers.sort(new Comparator<Dealer>() {
            @Override
            public int compare(Dealer o1, Dealer o2) {
                Double distance1 = null;
                Double distance2 = null;
                try {
                    distance1 = distanceBetweenDealerAndVehicle(
                            givenLatitude,
                            givenLongitude,
                            o1);

                    distance2 = distanceBetweenDealerAndVehicle(
                        givenLatitude,
                        givenLongitude,
                        o2);
                } catch (InvalidCoordinatesException e) {
                    throw new RuntimeException("Invalid coordinates");
                }

                return distance1.compareTo(distance2);

            }
        });
        return dealers;
    }

    //Might be wrong because coordinates passed are not transformed into x,y. I'm not entirely sure it's necessary though
    public List<Dealer> getDealersInsidePolygon(
            GetDealersInsidePolygonDto getDealersInsidePolygonDto){

        Path2D path = new Path2D.Double();
        List<PairDTO> pointList = getDealersInsidePolygonDto.getPolygonDTO().getPoints();
        path.moveTo(pointList.get(0).getKey(), pointList.get(0).getValue());
        for (int i = 1; i < pointList.size(); i++) {
            path.lineTo(pointList.get(i).getKey(), pointList.get(i).getValue());
        }


        List<Dealer> dealers = vehicleService.getVehiclesByAttributes(
                getDealersInsidePolygonDto.getModel(),
                getDealersInsidePolygonDto.getFuel(),
                getDealersInsidePolygonDto.getTransmission());

        List<Dealer> dealersInsidePolygon = new ArrayList<>();
        for(Dealer d : dealers){
            if(path.contains(d.getLatitude(), d.getLongitude())){
                dealersInsidePolygon.add(d);
            }
        }
        return dealersInsidePolygon;
    }
}
