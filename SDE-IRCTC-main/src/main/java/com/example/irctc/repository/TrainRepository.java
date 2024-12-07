package com.example.irctc.repository;

import com.example.irctc.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {


    List<Train> findBySourceAndDestination(String source, String destination);
}
