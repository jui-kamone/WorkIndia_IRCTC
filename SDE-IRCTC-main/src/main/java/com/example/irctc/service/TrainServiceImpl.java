package com.example.irctc.service;

import com.example.irctc.dto.TrainSearchRequest;
import com.example.irctc.entity.Train;
import com.example.irctc.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainServiceImpl implements TrainService{
    @Autowired
    private TrainRepository trainRepository;

    public Train addTrain(Train train) {
        return trainRepository.save(train);
    }


    //Trains to be stored in a list found between a source and destination
    @Override
    public List<Train> findTrainsBetweenStations(TrainSearchRequest searchRequest) {
        return trainRepository.findBySourceAndDestination(
                searchRequest.getSource(),
                searchRequest.getDestination()
        );
    }
}
