package com.mercedes.tiago.mercedesproject.persistence.repository;

import com.mercedes.tiago.mercedesproject.persistence.classes.Dealer;
import com.mercedes.tiago.mercedesproject.persistence.classes.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{
    Vehicle findByidString(String email);

//    @Query("select v from Vehicle v where v.model=?1")
    List<Vehicle> getVehiclesByModel(String model);

    List<Vehicle> getVehiclesByFuel(String fuel);

    List<Vehicle> getVehiclesByTransmission(String transmission);


    @Query("select v from Vehicle v where v.dealer.idString = ?1")
    List<Vehicle> getVehiclesByDealer(String dealerName);

    @Query("select v.dealer from Vehicle v where v.model = ?1 and v.fuel = ?2 and v.transmission =?3")
    List<Dealer> getVehiclesWithAttributes(String model, String fuel, String transmission);

}
