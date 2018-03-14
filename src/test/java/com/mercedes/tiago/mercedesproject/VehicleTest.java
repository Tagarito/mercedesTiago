package com.mercedes.tiago.mercedesproject;

import com.mercedes.tiago.mercedesproject.exception.AvailabilityMapContainsNonHoursException;
import com.mercedes.tiago.mercedesproject.exception.AvailabilityMapContainsNonWeekDaysException;
import com.mercedes.tiago.mercedesproject.exception.VehicleAlreadyExistsException;
import com.mercedes.tiago.mercedesproject.persistence.classes.AvailabilityHours;
import com.mercedes.tiago.mercedesproject.persistence.classes.Booking;
import com.mercedes.tiago.mercedesproject.persistence.classes.Dealer;
import com.mercedes.tiago.mercedesproject.persistence.classes.Vehicle;
import com.mercedes.tiago.mercedesproject.persistence.repository.AvailabilityHoursRepository;
import com.mercedes.tiago.mercedesproject.persistence.repository.DealerRepository;
import com.mercedes.tiago.mercedesproject.persistence.repository.VehicleRepository;
import com.mercedes.tiago.mercedesproject.service.VehicleService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class VehicleTest {

//    @Autowired
//    private TestEntityManager testEntityManager;

//    @Autowired
//    private TestEntityManager testEntityManager;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private AvailabilityHoursRepository availabilityHoursRepository;

//    @Autowired
//    private VehicleService vehicleService;


    private final String idString = "test1";
    private final String model = "model1";
    private final String fuel = "fuel1";
    private final String transmission = "transmission1";
    private final Long createdAtMilliseconds = DateTime.now().getMillis();
    private AvailabilityHours availabilityHours;
    private AvailabilityHours availabilityHours2;
    private Map<String, AvailabilityHours> availabilityHoursMap;
    private Dealer dealer;
    private Dealer dealerMock;
    private Booking booking1;
    private Booking booking2;
    private List<Booking> bookings;
    private final String dealerId = "dealer1";
    private final String dealerName = "dealerName1";
    private final double dealerLatitude = 30.2;
    private final double dealerLongitude = 66.3;
    private Vehicle vehicle;
    private List<Vehicle> vehicles;
    private List<String> closed = new ArrayList<>();
    private final Long dealerCreatedAtMilliseconds = DateTime.now().getMillis();

    @Before
    public void setup(){
//        vehicleRepositoryMock = Mockito.mock(VehicleRepository.class);
        vehicle = new Vehicle();
        vehicle.setIdString(idString);
        vehicle.setModel(model);
        vehicle.setFuel(fuel);
        vehicle.setTransmission(transmission);
        vehicle.setCreatedAtMilliseconds(createdAtMilliseconds);
        availabilityHours = new AvailabilityHours();
        Map<Long , String> availabilityHours_hours = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("HHmm");


        //Reason for cancelling which is the second parameter are initialized to ""
        availabilityHours_hours.put(formatter.parseDateTime("1030").getMillis(), "");
        availabilityHours_hours.put(formatter.parseDateTime("0923").getMillis(), "");
        availabilityHours.setHours(availabilityHours_hours);
        availabilityHours.setDayOfWeek("MONDAY");
        availabilityHours_hours.put(formatter.parseDateTime("1530").getMillis(), "");
        availabilityHours_hours.put(formatter.parseDateTime("2359").getMillis(), "");
        availabilityHours2 = new AvailabilityHours();
        availabilityHours2.setHours(availabilityHours_hours);
        availabilityHours2.setDayOfWeek("FRIDAY");
        availabilityHoursMap = new HashMap<>();
        availabilityHoursMap.put("MONDAY", availabilityHours);
        availabilityHoursMap.put("FRIDAY", availabilityHours2);
        vehicle.setAvailability(availabilityHoursMap);


        dealer = new Dealer();
        booking1 = new Booking();
        booking2 = new Booking();
        bookings = new ArrayList<>();
        dealerMock = mock(Dealer.class);
        dealer.setIdString(dealerId);
        when(dealerMock.getIdString()).thenReturn(dealerId);
        dealer.setName(dealerName);
        when(dealerMock.getName()).thenReturn(dealerName);
        dealer.setLatitude(dealerLatitude);
        when(dealerMock.getLatitude()).thenReturn(dealerLatitude);
        dealer.setLongitude(dealerLongitude);
        when(dealerMock.getLongitude()).thenReturn(dealerLongitude);
        vehicles = new ArrayList<>();
        vehicles.add(vehicle);
        dealer.setVehicles(vehicles);
        when(dealerMock.getVehicles()).thenReturn(vehicles);
        closed.add("WEDNESDAY");
        closed.add("SUNDAY");
        dealer.setClosed(closed);
        when(dealerMock.getClosed()).thenReturn(closed);
        dealer.setCreatedAtMilliseconds(dealerCreatedAtMilliseconds);
        when(dealerMock.getCreatedAtMilliseconds()).thenReturn(dealerCreatedAtMilliseconds);

        vehicle.setDealer(dealer);


    }

    @Test
    public void testObjectCreation(){
        assertThat(vehicle).isNotNull();
        assertThat(vehicle.getIdString()).isEqualTo(idString);
        assertThat(vehicle.getFuel()).isEqualTo(fuel);
        assertThat(vehicle.getModel()).isEqualTo(model);
        assertThat(vehicle.getTransmission()).isEqualTo(transmission);
        assertThat(vehicle.getAvailability()).isEqualTo(availabilityHoursMap);
        assertThat(vehicle.getDealer().getIdString()).isEqualTo(dealer.getIdString());
        assertThat(vehicle.getDealer().getName()).isEqualTo(dealer.getName());
        assertThat(vehicle.getDealer().getLatitude()).isEqualTo(dealer.getLatitude());
        assertThat(vehicle.getDealer().getLongitude()).isEqualTo(dealer.getLongitude());
        assertThat(vehicle.getDealer().getCreatedAtMilliseconds()).isEqualTo(dealer.getCreatedAtMilliseconds());
        assertThat(vehicle.getDealer().getClosed()).isEqualTo(dealer.getClosed());
        List<Vehicle> vehiclesObject = vehicle.getDealer().getVehicles();
        List<Vehicle> vehiclestoSave = dealer.getVehicles();

        assertThat(vehiclesObject.size()).isEqualTo(vehiclestoSave.size());
        for (int i = 0; i < vehiclesObject.size(); i++) {
            assertThat(vehiclesObject.get(i).getIdString()).isEqualTo(vehiclestoSave.get(i).getIdString());
        }
    }

    private void setAvailabilityHoursVehicleIdAndSave(String vehicleId){
        availabilityHours.setVehicleId(vehicleId);
        availabilityHours2.setVehicleId(vehicleId);
        availabilityHoursRepository.save(availabilityHours);
        availabilityHoursRepository.save(availabilityHours2);
    }

    @Test
    public void saveTest(){
        dealer.setIdString(dealerId+1);
        when(dealerMock.getIdString()).thenReturn(dealerId+1);
        dealer = dealerRepository.save(dealer);
        vehicle.setIdString(idString+1);
        setAvailabilityHoursVehicleIdAndSave(vehicle.getIdString());
        vehicle = vehicleRepository.save(vehicle);
        Vehicle aVehicle = vehicleRepository.getOne(vehicle.getId());

        assertThat(vehicle).isNotNull();
        assertThat(aVehicle).isNotNull();
        assertThat(vehicle.getId()).isEqualTo(aVehicle.getId());
    }

    @Test
    public void saveTest_FullObject(){
        dealer.setIdString(dealerId+2);
        when(dealerMock.getIdString()).thenReturn(dealerId+2);
        dealer = dealerRepository.save(dealer);
        vehicle.setIdString(idString+2);
        setAvailabilityHoursVehicleIdAndSave(vehicle.getIdString());
        vehicle = vehicleRepository.save(vehicle);

        Vehicle aVehicle = vehicleRepository.getOne(vehicle.getId());

        assertThat(vehicle).isNotNull();
        assertThat(aVehicle).isNotNull();
        assertThat(vehicle.getId()).isEqualTo(aVehicle.getId());
        assertThat(vehicle.getIdString()).isEqualTo(aVehicle.getIdString());
        assertThat(vehicle.getModel()).isEqualTo(aVehicle.getModel());
        assertThat(vehicle.getFuel()).isEqualTo(aVehicle.getFuel());
        assertThat(vehicle.getTransmission()).isEqualTo(aVehicle.getTransmission());
        assertThat(vehicle.getCreatedAtMilliseconds()).isEqualTo(aVehicle.getCreatedAtMilliseconds());

        Dealer checkDealer = aVehicle.getDealer();
        assertThat(dealer.getId()).isEqualTo(checkDealer.getId());
        assertThat(dealerMock.getIdString()).isEqualTo(checkDealer.getIdString());
        assertThat(dealerMock.getName()).isEqualTo(checkDealer.getName());
        assertThat(dealerMock.getLatitude()).isEqualTo(checkDealer.getLatitude());
        assertThat(dealerMock.getLongitude()).isEqualTo(checkDealer.getLongitude());
//        assertThat(dealerMock.getVehicles()).isEqualTo(checkDealer.getVehicles());
        List<Vehicle> checkVehicles = checkDealer.getVehicles();
        List<Vehicle> mockVehicles = dealerMock.getVehicles();
        assertThat(mockVehicles.size()).isEqualTo(checkVehicles.size());
        for (int i = 0; i < checkVehicles.size(); i++) {
            //Might give problems if hibernate reordenates list
            assertThat(checkVehicles.get(i).getIdString()).isEqualTo(mockVehicles.get(i).getIdString());
        }
        assertThat(dealerMock.getClosed()).isEqualTo(checkDealer.getClosed());
        assertThat(dealerMock.getCreatedAtMilliseconds()).isEqualTo(checkDealer.getCreatedAtMilliseconds());

    }

    @Test
    public void SERVICE_addVehicle() throws AvailabilityMapContainsNonHoursException, VehicleAlreadyExistsException, AvailabilityMapContainsNonWeekDaysException {
        dealer.setIdString(dealerId+3);
        when(dealerMock.getIdString()).thenReturn(dealerId+3);
        dealer = dealerRepository.save(dealer);
        vehicle.setIdString(idString+3);
        setAvailabilityHoursVehicleIdAndSave(vehicle.getIdString());


        HashMap<String, List<String>> availabilityDto = transformIntoDTO(availabilityHoursMap);
        Vehicle vehicleServiceReturn = vehicleService.addVehicle(dealer, vehicle.getIdString(), model, fuel, transmission, availabilityDto);
        assertThat(vehicle.getIdString()).isEqualTo(vehicleServiceReturn.getIdString());
        assertThat(vehicle.getModel()).isEqualTo(vehicleServiceReturn.getModel());
        assertThat(vehicle.getFuel()).isEqualTo(vehicleServiceReturn.getFuel());
        assertThat(vehicle.getTransmission()).isEqualTo(vehicleServiceReturn.getTransmission());

        Map<String, AvailabilityHours> vehicleAvailability = vehicle.getAvailability();
        Map<String, AvailabilityHours> vehicleServiceReturnAvailability = vehicleServiceReturn.getAvailability();
        assertThat(vehicleAvailability.size()).isEqualTo(vehicleServiceReturnAvailability.size());
        for(Map.Entry<String, AvailabilityHours> a : vehicleAvailability.entrySet()){
            AvailabilityHours normal = a.getValue();
            AvailabilityHours returned = vehicleServiceReturnAvailability.get(a.getKey());
            assertThat(normal.getHours()).isEqualTo(returned.getHours());
            assertThat(normal.getVehicleId()).isEqualTo(returned.getVehicleId());
            assertThat(normal.getDayOfWeek()).isEqualTo(returned.getDayOfWeek());
        }

    }

    @Test(expected = VehicleAlreadyExistsException.class)
    public void SERVICE_addVehicleThatAlreadyExists() throws AvailabilityMapContainsNonHoursException, VehicleAlreadyExistsException, AvailabilityMapContainsNonWeekDaysException {
        dealer.setIdString(dealerId+4);
        when(dealerMock.getIdString()).thenReturn(dealerId+3);
        dealer = dealerRepository.save(dealer);
        vehicle.setIdString(idString+4);
        setAvailabilityHoursVehicleIdAndSave(vehicle.getIdString());

        HashMap<String, List<String>> availabilityDto = transformIntoDTO(availabilityHoursMap);
        Vehicle vehicleServiceReturn = vehicleService.addVehicle(dealer, vehicle.getIdString(), model, fuel, transmission, availabilityDto);
        Vehicle vehicleServiceReturn2 = vehicleService.addVehicle(dealer, vehicle.getIdString(), model, fuel, transmission, availabilityDto);

    }


        private HashMap<String, List<String>> transformIntoDTO(Map<String, AvailabilityHours> availabilityHoursMap) {
        HashMap<String, List<String>> copyAvailability = new HashMap<>();
        List<String> copyListString;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HHmm");
        for(Map.Entry<String, AvailabilityHours> a: availabilityHoursMap.entrySet()){
            copyListString = new ArrayList<>();
            for(Map.Entry<Long, String> bookedHour:a.getValue().getHours().entrySet()){
                //pass hours to string
                copyListString.add(new DateTime(bookedHour.getKey()).toString(formatter));
            }
            copyAvailability.put(a.getKey(), copyListString);
        }
        return copyAvailability;
    }
}
