//package com.mercedes.tiago.mercedesproject;
//
//import com.mercedes.tiago.mercedesproject.exception.DealerNotFoundException;
//import com.mercedes.tiago.mercedesproject.persistence.classes.Dealer;
//import com.mercedes.tiago.mercedesproject.persistence.classes.Vehicle;
//import com.mercedes.tiago.mercedesproject.persistence.repository.DealerRepository;
//import com.mercedes.tiago.mercedesproject.service.DealerService;
//import com.mercedes.tiago.mercedesproject.service.VehicleService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.not;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.mock;
//
//
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = MercedesProjectApplication.class)
//@DataJpaTest
//public class DealerServiceImplTest {
//
//    @TestConfiguration
//    static class DealerServiceImplTestContextConfiguration{
//        @Bean
//        public DealerService dealerService(){
//            return new DealerService();
//        }
//
//        @Bean
//        public VehicleService vehicleService(){
//            return new VehicleService();
//        }
//    }
//
//    @Autowired
//    private DealerService dealerService;
//
//    @Autowired
//    private VehicleService vehicleService;
//
//    @Autowired
//    private DealerRepository dealerRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Before
//    public void setup(){
//
//        String idString ="ab49f56a-451d-4721-8319-efdca5e69680";
//        String name = "MB Albufeira";
//        double latitude = 37.104404;
//        double longitude = -8.236308;
//        List<String> closed = new ArrayList<>();
//        closed.add("tuesday");
//        closed.add("wednesday");
//
//        Dealer dealer = new Dealer();
//        dealer.setId(idString);
//        dealer.setName(name);
//        dealer.setLatitude(latitude);
//        dealer.setLongitude(longitude);
//        dealer.setClosed(closed);
//        entityManager.persist(dealer);
//        entityManager.flush();
//
//
//        //Not sure how to deal with oneToMany unit testing. Plus I would need to simulate vehicle
//        //creation as well as return an id. Not quite sure how this works.
//
////        List<VehicleDTO> vehicles = new ArrayList<>();
////        VehicleDTO vehicleDTO = new VehicleDTO();
////        vehicleDTO.setId("3928f780-295b-4dd0-8cc9-28c0738398d9");
////        vehicleDTO.setModel("AMG");
////        vehicleDTO.setFuel("ELECTRIC");
////        vehicleDTO.setTransmission("AUTO");
////        HashMap<String, List<String>> availability = new HashMap<>();
////        List<String> times = new ArrayList<>();
////        times.add("1000");
////        times.add("1030");
////        availability.put("thursday", times);
////        vehicleDTO.setAvailability(availability);
////        vehicles.add(vehicleDTO);
////                dealerDTO.setVehicles(vehicles);
//
//
//
////        Mockito.when(dealerRepository.findByidString(dealer.getId())).thenReturn(dealer);
//    }
//
//    @Test
//    public void whenValidName_thenDealerShouldBeFound(){
//        String idString ="ab49f56a-451d-4721-8319-efdca5e69680";
//        String name = "MB Albufeira";
//        double latitude = 37.104404;
//        double longitude = -8.236308;
//        List<String> closed = new ArrayList<>();
//        closed.add("tuesday");
//        closed.add("wednesday");
//        try {
//            Dealer dealer = dealerService.getDealer(idString);
//            //        //then
//            assertThat(dealer.getId()).isEqualTo(idString);
//            assertThat(dealer.getName()).isEqualTo(name);
//            assertThat(dealer.getLatitude()).isEqualTo(latitude);
//            assertThat(dealer.getLongitude()).isEqualTo(longitude);
//            assertThat(dealer.getClosed()).isEqualTo(closed);
//        } catch (DealerNotFoundException e) {
//            throw new RuntimeException("Should never happen, dealerService test");
//        }
//    }
//
//    @Test
//    public void whenInvalidName_thenDealerShouldNotBeFound(){
//        String idString ="ab49f56a-451d-4721-8319-efdca5e69680";
//
////        Dealer dealer = dealerService.getDealer()
//    }
//}
