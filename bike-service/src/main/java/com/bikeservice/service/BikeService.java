package com.bikeservice.service;

import com.bikeservice.entities.Bike;
import com.bikeservice.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService {

    @Autowired
    private BikeRepository  bikeRepository;


    public List<Bike> getAllBikes() {
        return bikeRepository.findAll();
    }

    public Bike getBikeById(int id) {
        return bikeRepository.findById(id).orElse(null);
    }
    public  Bike saveBike(Bike bike) {
        Bike newBike= bikeRepository.save(bike);
        return newBike;
    }

    public List<Bike>  byUserId(int userId) {
        return bikeRepository.findByUserId(userId);
    }

}
