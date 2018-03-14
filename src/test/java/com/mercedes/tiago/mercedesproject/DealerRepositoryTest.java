package com.mercedes.tiago.mercedesproject;

import com.mercedes.tiago.mercedesproject.persistence.classes.Dealer;
import com.mercedes.tiago.mercedesproject.persistence.repository.DealerRepository;
import com.mercedes.tiago.mercedesproject.service.DealerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class DealerRepositoryTest {


    @Autowired
    private DealerRepository dealerRepository;

    @Test
    public void whenFindByidString_thenReturnDealer(){
        String idString = "testidstring";
        //given
        Dealer dealer = new Dealer();
        dealer.setIdString(idString);
        dealerRepository.save(dealer);

        //when

        Dealer found = dealerRepository.findByidString(idString);

        //then
        assertThat(found.getIdString()).isEqualTo(dealer.getIdString());
    }

//    @Test
//    public void whenFindByidString_thenReturnDealer_complete(){
//
//        //given
//
//        DealerDTO dealerDTO = new DealerDTO();
//        dealerDTO.setId("ab49f56a-451d-4721-8319-efdca5e69680");
//        dealerDTO.setName("MB Albufeira");
//        dealerDTO.setLatitude(37.104404);
//        dealerDTO.setLongitude(-8.236308);
//        List<String> closed = new ArrayList<>();
//        closed.add("tuesday");
//        closed.add("wednesday");
//        dealerDTO.setClosed(closed);
//        List<VehicleDTO> vehicles = new ArrayList<>();
//        VehicleDTO vehicleDTO = new VehicleDTO();
//        vehicleDTO.setId("3928f780-295b-4dd0-8cc9-28c0738398d9");
//        vehicleDTO.setModel("AMG");
//        vehicleDTO.setFuel("ELECTRIC");
//        vehicleDTO.setTransmission("AUTO");
//        HashMap<String, List<String>> availability = new HashMap<>();
//        List<String> times = new ArrayList<>();
//        times.add("1000");
//        times.add("1030");
//        availability.put("thursday", times);
//        vehicleDTO.setAvailability(availability);
//        vehicles.add(vehicleDTO);
//        dealerDTO.setVehicles(vehicles);
//        Dealer dealer = new Dealer();
//        dealer.setId(dealerDTO.getId());
//        dealer.setName(dealerDTO.getName());
//        dealer.setLatitude(dealerDTO.getLatitude());
//        dealer.setLongitude(dealerDTO.getLongitude());
//
//        dealer.setClosed(dealerDTO.getClosed());
//
//        //DOESN'T TEST THE VEHICLES RELATIONSHIP
//
//        entityManager.persist(dealer);
//        entityManager.flush();
//
//        //when
//        Dealer found = dealerRepository.findByidString(dealer.getId());
//
//        //then
//        assertThat(found.getId()).isEqualTo(dealer.getId());
//        assertThat(found.getName()).isEqualTo(dealer.getName());
//        assertThat(found.getLatitude()).isEqualTo(dealer.getLatitude());
//        assertThat(found.getLongitude()).isEqualTo(dealer.getLongitude());
//        assertThat(found.getClosed()).isEqualTo(dealer.getClosed());
//    }
}
