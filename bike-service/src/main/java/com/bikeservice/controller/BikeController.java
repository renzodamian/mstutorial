package com.bikeservice.controller;

import com.bikeservice.entities.Bike;
import com.bikeservice.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike")
public class BikeController {
    @Autowired
    private BikeService bikeService;

    @GetMapping
    public ResponseEntity<List<Bike>> listCar(){
        List<Bike> bikes = bikeService.getAllBikes();
        if (bikes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Bike> getBike(@PathVariable ("id") int id){
        Bike bike = bikeService.getBikeById(id);
        if (bike == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bike);
    }

    @PostMapping
    public ResponseEntity <Bike> addBike(@RequestBody Bike bike){
        Bike newBike = bikeService.saveBike(bike);
        return ResponseEntity.ok(newBike);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity <List<Bike>> getCarByUserId(@PathVariable("userId") int userId){
        List<Bike> bikes = bikeService.byUserId(userId);
        return ResponseEntity.ok(bikes);
    }
}
