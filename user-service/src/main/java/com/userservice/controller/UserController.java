package com.userservice.controller;

import com.userservice.Model.Bike;
import com.userservice.Model.Car;
import com.userservice.entities.User;
import com.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity <List<User>> listUser(){
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id")int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.save(user);
        return ResponseEntity.ok(newUser);
    }
    //RestTemplate
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> listCars(@PathVariable("userId")int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Car> cars = userService.getCars(id);
        return ResponseEntity.ok(cars);
    }
    //RestTemplate
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> listBikes(@PathVariable("userId")int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Bike> bikes = userService.getBikes(id);
        return ResponseEntity.ok(bikes);
    }
    //FeignClient
    @PostMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable("userId")int userId, @RequestBody Car car){
        if (userService.getUserById(userId )==null) {
            return ResponseEntity.notFound().build();
        }
        Car newCar = userService.saveCar(userId, car);
        return ResponseEntity.ok(newCar);
    }
    //FeignClient
    @PostMapping("/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId")int userId, @RequestBody Bike bike){
        if (userService.getUserById(userId )==null) {
            return ResponseEntity.notFound().build();
        }
        Bike newBike= userService.saveBike(userId, bike);
        return ResponseEntity.ok(newBike);
    }
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getAllVehicle(@PathVariable("userId")int userId) {
       Map<String, Object> result = userService.getUserAndVehicles(userId);
        return ResponseEntity.ok(result);

    }

}
