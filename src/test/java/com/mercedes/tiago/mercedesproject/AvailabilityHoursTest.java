package com.mercedes.tiago.mercedesproject;

import com.mercedes.tiago.mercedesproject.persistence.classes.AvailabilityHours;
import com.mercedes.tiago.mercedesproject.persistence.repository.AvailabilityHoursRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class AvailabilityHoursTest {

    @Autowired
    private AvailabilityHoursRepository availabilityHoursRepository;

    private Map<Long, String> hours;
    private final String vehicleId = "vehicle1";
    private final String dayOfWeek = "TUESDAY";
    private final Long long1 = new Long(3);
    private final String hour1 = "0930";
    private final Long long2 = new Long(15);
    private final String hour2 = "2200";
    private AvailabilityHours availabilityHours;

    @Before
    public void setup(){
        hours = new HashMap<>();
        hours.put(long1, hour1);
        hours.put(long2,hour2);

        AvailabilityHours availabilityHours = new AvailabilityHours();
        availabilityHours.setVehicleId(vehicleId);
        availabilityHours.setDayOfWeek(dayOfWeek);
        availabilityHours.setHours(hours);
        this.availabilityHours = availabilityHours;
    }

    @Test
    public void testObjectCreation(){
        assertThat(availabilityHours).isNotNull();
        assertThat(availabilityHours.getDayOfWeek()).isEqualTo(dayOfWeek);
        assertThat(availabilityHours.getVehicleId()).isEqualTo(vehicleId);
        assertThat(availabilityHours.getHours()).isEqualTo(hours);
    }

    @Test
    public void saveTest(){
        AvailabilityHours availabilityHours = new AvailabilityHours();
        availabilityHours = availabilityHoursRepository.save(availabilityHours);

        AvailabilityHours testAvailabilityHours = availabilityHoursRepository.getOne(availabilityHours.getId());

        assertThat(availabilityHours).isNotNull();
        assertThat(testAvailabilityHours).isNotNull();
        assertThat(availabilityHours.getId()).isEqualTo(testAvailabilityHours.getId());
    }

    @Test
    public void saveTest_FullObject(){
        AvailabilityHours availabilityHoursObject;
        availabilityHoursObject = availabilityHoursRepository.save(availabilityHours);

        AvailabilityHours testAvailabilityHours = availabilityHoursRepository.getOne(availabilityHoursObject.getId());

        assertThat(availabilityHoursObject).isNotNull();
        assertThat(testAvailabilityHours).isNotNull();
        assertThat(testAvailabilityHours.getId()).isEqualTo(availabilityHoursObject.getId());
        assertThat(testAvailabilityHours.getDayOfWeek()).isEqualTo(availabilityHours.getDayOfWeek());
        assertThat(testAvailabilityHours.getVehicleId()).isEqualTo(availabilityHours.getVehicleId());
        assertThat(testAvailabilityHours.getHours()).isEqualTo(availabilityHours.getHours());
        assertThat(testAvailabilityHours).isEqualTo(availabilityHours);
    }
}
