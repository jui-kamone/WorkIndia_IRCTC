package com.example.irctc.service;

import com.example.irctc.dto.TrainSearchRequest;
import com.example.irctc.entity.Train;

import java.util.List;

public interface TrainService {
    List<Train> findTrainsBetweenStations(TrainSearchRequest searchRequest);
}