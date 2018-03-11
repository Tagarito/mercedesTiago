package com.mercedes.tiago.mercedesproject.persistence.repository;

import com.mercedes.tiago.mercedesproject.persistence.classes.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {
    Dealer findByidString(String email);

}
