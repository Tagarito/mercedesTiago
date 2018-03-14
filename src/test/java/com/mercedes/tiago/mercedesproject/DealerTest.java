package com.mercedes.tiago.mercedesproject;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.mercedes.tiago.mercedesproject.dto.*;
import com.mercedes.tiago.mercedesproject.exception.*;
import com.mercedes.tiago.mercedesproject.persistence.classes.Dealer;
import com.mercedes.tiago.mercedesproject.service.DealerService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.mercedes.tiago.mercedesproject.MercedesProjectApplication.objectMapper;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class DealerTest {

    @Autowired
    private DealerService dealerService;

    private static boolean initialized = false;

    private final String dealerId = "testdealerid1";
    private final String dealerName = "testdealername1";
    private final double dealerLatitude = 90;
    private final double dealerLongitude = 180;
    private final String vehicleId = "testdealervehicleid1";
    private final String vehicleModel = "testdealervehiclemodel1";
    private final String vehicleFuel = "testdealervehiclefuel1";
    private final String vehicleTransmission = "testdealervehicletransmission1";




    public DealerTest() {

    }

    @Before
    public void setup(){
        try {
            if(!initialized) {
                objectMapper.registerModule(new JodaModule());
                byte[] initFile = Files.readAllBytes(Paths.get("src/main/resources/tests.json"));
                RootDTO rootDTO = objectMapper.readValue(initFile, RootDTO.class);
                System.out.println(rootDTO);
                System.out.println(rootDTO.getDealers());
                dealerService.addDealers(rootDTO.getDealers());
                initialized = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidCoordinatesException e) {
            e.printStackTrace();
        } catch (DealerAlreadyExistsException e) {
            e.printStackTrace();
        } catch (AvailabilityMapContainsNonHoursException e) {
            e.printStackTrace();
        } catch (ClosedListContainsNonWeekDaysException e) {
            e.printStackTrace();
        } catch (VehicleAlreadyExistsException e) {
            e.printStackTrace();
        } catch (AvailabilityMapContainsNonWeekDaysException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_DealerService_Success() throws AvailabilityMapContainsNonWeekDaysException, ClosedListContainsNonWeekDaysException, InvalidCoordinatesException, VehicleAlreadyExistsException, DealerAlreadyExistsException, AvailabilityMapContainsNonHoursException {
        DealerDTO dealerDTO = null;
        try {
            dealerDTO = objectMapper.readValue("{\n" +
                    "      \"id\": \""+dealerId+1+"\",\n" +
                    "      \"name\": \""+dealerName+"\",\n" +
                    "      \"latitude\":"+dealerLatitude+",\n" +
                    "      \"longitude\":"+dealerLongitude+",\n" +
                    "      \"closed\": [\n" +
                    "        \"tuesday\",\n" +
                    "        \"wednesday\"\n" +
                    "      ],\n" +
                    "      \"vehicles\": [\n" +
                    "        {\n" +
                    "          \"id\": \""+vehicleId+1+"\",\n" +
                    "          \"model\": \""+vehicleModel+"\",\n" +
                    "          \"fuel\": \""+vehicleFuel+"\",\n" +
                    "          \"transmission\": \""+vehicleTransmission+"\",\n" +
                    "          \"availability\": {\n" +
                    "            \"thursday\": [\n" +
                    "              \"1000\",\n" +
                    "              \"1030\"\n" +
                    "            ],\n" +
                    "            \"monday\": [\n" +
                    "              \"1000\",\n" +
                    "              \"1030\"\n" +
                    "            ]\n" +
                    "          }\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }", DealerDTO.class);
            dealerService.addDealerDto(dealerDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = VehicleAlreadyExistsException.class)
    public void test_DealerService_VehicleAlreadyExists() throws AvailabilityMapContainsNonWeekDaysException, ClosedListContainsNonWeekDaysException, InvalidCoordinatesException, VehicleAlreadyExistsException, DealerAlreadyExistsException, AvailabilityMapContainsNonHoursException {
        try {
            DealerDTO dealerDTO = dealerDTO = objectMapper.readValue("{\n" +
                    "      \"id\": \""+dealerId+2+"\",\n" +
                    "      \"name\": \""+dealerName+"\",\n" +
                    "      \"latitude\":"+dealerLatitude+",\n" +
                    "      \"longitude\":"+dealerLongitude+",\n" +
                    "      \"closed\": [\n" +
                    "        \"tuesday\",\n" +
                    "        \"wednesday\"\n" +
                    "      ],\n" +
                    "      \"vehicles\": [\n" +
                    "        {\n" +
                    "          \"id\": \""+vehicleId+2+"\",\n" +
                    "          \"model\": \""+vehicleModel+"\",\n" +
                    "          \"fuel\": \""+vehicleFuel+"\",\n" +
                    "          \"transmission\": \""+vehicleTransmission+"\",\n" +
                    "          \"availability\": {\n" +
                    "            \"thursday\": [\n" +
                    "              \"1000\",\n" +
                    "              \"1030\"\n" +
                    "            ],\n" +
                    "            \"monday\": [\n" +
                    "              \"1000\",\n" +
                    "              \"1030\"\n" +
                    "            ]\n" +
                    "          }\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }", DealerDTO.class);
            DealerDTO dealerDTOSameVehicle = objectMapper.readValue("{\n" +
                    "      \"id\": \""+dealerId+2+1+"\",\n" +
                    "      \"name\": \""+dealerName+"\",\n" +
                    "      \"latitude\":"+dealerLatitude+",\n" +
                    "      \"longitude\":"+dealerLongitude+",\n" +
                    "      \"closed\": [\n" +
                    "        \"tuesday\",\n" +
                    "        \"wednesday\"\n" +
                    "      ],\n" +
                    "      \"vehicles\": [\n" +
                    "        {\n" +
                    "          \"id\": \""+vehicleId+2+"\",\n" +
                    "          \"model\": \""+vehicleModel+"\",\n" +
                    "          \"fuel\": \""+vehicleFuel+"\",\n" +
                    "          \"transmission\": \""+vehicleTransmission+"\",\n" +
                    "          \"availability\": {\n" +
                    "            \"thursday\": [\n" +
                    "              \"1000\",\n" +
                    "              \"1030\"\n" +
                    "            ],\n" +
                    "            \"monday\": [\n" +
                    "              \"1000\",\n" +
                    "              \"1030\"\n" +
                    "            ]\n" +
                    "          }\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }", DealerDTO.class);

            dealerService.addDealerDto(dealerDTO);
            dealerService.addDealerDto(dealerDTOSameVehicle);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = DealerAlreadyExistsException.class)
    public void test_DealerService_DealerAlreadyExists() throws AvailabilityMapContainsNonWeekDaysException, ClosedListContainsNonWeekDaysException, InvalidCoordinatesException, VehicleAlreadyExistsException, DealerAlreadyExistsException, AvailabilityMapContainsNonHoursException {
        try {
            DealerDTO dealerDTO = dealerDTO = objectMapper.readValue("{\n" +
                    "      \"id\": \""+dealerId+3+"\",\n" +
                    "      \"name\": \""+dealerName+"\",\n" +
                    "      \"latitude\":"+dealerLatitude+",\n" +
                    "      \"longitude\":"+dealerLongitude+",\n" +
                    "      \"closed\": [\n" +
                    "        \"tuesday\",\n" +
                    "        \"wednesday\"\n" +
                    "      ],\n" +
                    "      \"vehicles\": [\n" +
                    "        {\n" +
                    "          \"id\": \""+vehicleId+3+"\",\n" +
                    "          \"model\": \""+vehicleModel+"\",\n" +
                    "          \"fuel\": \""+vehicleFuel+"\",\n" +
                    "          \"transmission\": \""+vehicleTransmission+"\",\n" +
                    "          \"availability\": {\n" +
                    "            \"thursday\": [\n" +
                    "              \"1000\",\n" +
                    "              \"1030\"\n" +
                    "            ],\n" +
                    "            \"monday\": [\n" +
                    "              \"1000\",\n" +
                    "              \"1030\"\n" +
                    "            ]\n" +
                    "          }\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }", DealerDTO.class);
            dealerService.addDealerDto(dealerDTO);
            dealerService.addDealerDto(dealerDTO);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_DealerService_getDealer_Success() throws AvailabilityMapContainsNonWeekDaysException, ClosedListContainsNonWeekDaysException, InvalidCoordinatesException, VehicleAlreadyExistsException, DealerAlreadyExistsException, AvailabilityMapContainsNonHoursException, DealerNotFoundException {
        try {
            DealerDTO dealerDTO = dealerDTO = objectMapper.readValue("{\n" +
                    "      \"id\": \""+dealerId+4+"\",\n" +
                    "      \"name\": \""+dealerName+"\",\n" +
                    "      \"latitude\":"+dealerLatitude+",\n" +
                    "      \"longitude\":"+dealerLongitude+",\n" +
                    "      \"closed\": [\n" +
                    "        \"tuesday\",\n" +
                    "        \"wednesday\"\n" +
                    "      ],\n" +
                    "      \"vehicles\": [\n" +
                    "        {\n" +
                    "          \"id\": \""+vehicleId+4+"\",\n" +
                    "          \"model\": \""+vehicleModel+"\",\n" +
                    "          \"fuel\": \""+vehicleFuel+"\",\n" +
                    "          \"transmission\": \""+vehicleTransmission+"\",\n" +
                    "          \"availability\": {\n" +
                    "            \"thursday\": [\n" +
                    "              \"1000\",\n" +
                    "              \"1030\"\n" +
                    "            ],\n" +
                    "            \"monday\": [\n" +
                    "              \"1000\",\n" +
                    "              \"1030\"\n" +
                    "            ]\n" +
                    "          }\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }", DealerDTO.class);
            dealerService.addDealerDto(dealerDTO);
            dealerService.getDealer(dealerId+4);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = DealerNotFoundException.class)
    public void test_DealerService_getDealer_DealerNotFound() throws DealerNotFoundException {
        dealerService.getDealer(dealerId+5);
    }

    @Test
    public void test_DealerService_getClosestDealer() throws InvalidCoordinatesException {

        ClosestDealerDto closestDealerDto = new ClosestDealerDto();
        closestDealerDto.setLatitude(10.1);
        closestDealerDto.setLongitude(32.23);
        closestDealerDto.setModel("A");
        closestDealerDto.setFuel("ELECTRIC");
        closestDealerDto.setTransmission("AUTO");

        Dealer dealer = dealerService.getClosestDealer(closestDealerDto);
        assertThat(dealer.getIdString()).isEqualTo("bbcdbbad-5d0b-45ef-90ac-3581b997e063");
    }

    @Test
    public void test_DealerService_getClosestDealerList() throws InvalidCoordinatesException {

        ClosestDealerDto closestDealerDto = new ClosestDealerDto();
        closestDealerDto.setLatitude(10.1);
        closestDealerDto.setLongitude(32.23);
        closestDealerDto.setModel("E");
        closestDealerDto.setFuel("ELECTRIC");
        closestDealerDto.setTransmission("AUTO");

        List<Dealer> dealers = dealerService.getDealersSortedByDistance(closestDealerDto);
        assertThat(dealers.size()).isEqualTo(2);
        assertThat(dealers.get(0).getIdString()).isEqualTo("846679bd-5831-4286-969b-056e9c89d74c");
        assertThat(dealers.get(1).getIdString()).isEqualTo("d4f4d287-1ad6-4968-a8ff-e9e0009ad5d1");

    }

    @Test
    public void test_DealerService_getDealersInsidePolygon(){
        GetDealersInsidePolygonDto getDealersInsidePolygonDto = new GetDealersInsidePolygonDto();
        getDealersInsidePolygonDto.setModel("E");
        getDealersInsidePolygonDto.setFuel("ELECTRIC");
        getDealersInsidePolygonDto.setTransmission("AUTO");
        PolygonDTO polygonDTO = new PolygonDTO();
        PairDTO pairDTO = new PairDTO();
        pairDTO.setKey(40.0);
        pairDTO.setValue(0.0);
        PairDTO pairDTO1 = new PairDTO();
        pairDTO1.setKey(90.0);
        pairDTO1.setValue(0.0);
        PairDTO pairDTO2 = new PairDTO();
        pairDTO2.setKey(90.0);
        pairDTO2.setValue(-180.0);
        PairDTO pairDTO3 = new PairDTO();
        pairDTO3.setKey(40.0);
        pairDTO3.setValue(-180.0);
        List<PairDTO> pairDTOS = new ArrayList<>();
        pairDTOS.add(pairDTO);
        pairDTOS.add(pairDTO1);
        pairDTOS.add(pairDTO2);
        pairDTOS.add(pairDTO3);
        polygonDTO.setPoints(pairDTOS);
        getDealersInsidePolygonDto.setPolygonDTO(polygonDTO);

        List<Dealer> dealers = dealerService.getDealersInsidePolygon(getDealersInsidePolygonDto);
        assertThat(dealers.size()).isEqualTo(1);
        assertThat(dealers.get(0).getIdString()).isEqualTo("d4f4d287-1ad6-4968-a8ff-e9e0009ad5d1");

    }
}
