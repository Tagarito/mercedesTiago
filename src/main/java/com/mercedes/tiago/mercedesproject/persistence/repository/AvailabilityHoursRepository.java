package com.mercedes.tiago.mercedesproject.persistence.repository;

import com.mercedes.tiago.mercedesproject.persistence.classes.AvailabilityHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AvailabilityHoursRepository extends
        JpaRepository<AvailabilityHours, Long> {

}
