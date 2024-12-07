package com.example.irctc.controller;

import com.example.irctc.dto.TrainSearchRequest;
import com.example.irctc.entity.Train;
import com.example.irctc.service.TrainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trains")
public class TrainController {

    // API key for admin access
    private static final String ADMIN_API_KEY = "4fg8v4df8g4d8g7dg4df8hg47dfg";

    // Autowired bean for TrainService implementation
    @Autowired
    private TrainServiceImpl trainServiceImpl;

    // Endpoint to add a new train
    @PostMapping("/add")
    public ResponseEntity<String> addTrain(@RequestHeader("API-Key") String apiKey, @RequestBody Train train) {
        // Checking if the API key is valid
        if (!apiKey.equals(ADMIN_API_KEY)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        // Adding the train and returning response
        Train savedTrain = trainServiceImpl.addTrain(train);
        String message = "Train added between " + savedTrain.getSource() + " and " + savedTrain.getDestination();
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
    // Endpoint to find trains between 2 stations
    @GetMapping("/between")
    public ResponseEntity<List<Train>> getTrainsBetweenStations(
            @RequestBody TrainSearchRequest searchRequest) {
        List<Train> trains = trainServiceImpl.findTrainsBetweenStations(searchRequest);
        return ResponseEntity.ok(trains);
    }




}