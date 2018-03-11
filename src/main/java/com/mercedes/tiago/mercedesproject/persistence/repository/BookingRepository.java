package com.mercedes.tiago.mercedesproject.persistence.repository;

import com.mercedes.tiago.mercedesproject.persistence.classes.Booking;
import com.mercedes.tiago.mercedesproject.persistence.classes.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{
    Booking findByidString(String email);

}
