package com.example.irctc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trains")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_id")
    private Long id;
    @Column(nullable = false)
    private String trainName;
    @Column(nullable = false)
    private String source;
    @Column(nullable = false)
    private String sourceTime;
    @Column(nullable = false)
    private String destination;
    @Column(nullable = false)
    private String destinationTime;
    @Column(nullable = false)
    private int seats;
}