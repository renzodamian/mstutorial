package com.userservice.controller;

import com.userservice.Model.Bike;
import com.userservice.Model.Car;
import com.userservice.entities.User;
import com.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackListCars")
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
    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallbackListBikes")
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
    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackSaveCar")
    @PostMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable("userId")int userId, @RequestBody Car car){
        if (userService.getUserById(userId )==null) {
            return ResponseEntity.notFound().build();
        }
        Car newCar = userService.saveCar(userId, car);
        return ResponseEntity.ok(newCar);
    }
    //FeignClient

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallbackSaveBike")
    @PostMapping("/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId")int userId, @RequestBody Bike bike){
        if (userService.getUserById(userId )==null) {
            return ResponseEntity.notFound().build();
        }
        Bike newBike= userService.saveBike(userId, bike);
        return ResponseEntity.ok(newBike);
    }
    @CircuitBreaker(name = "allCB", fallbackMethod = "fallbackGetAll")
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getAllVehicle(@PathVariable("userId")int userId) {
       Map<String, Object> result = userService.getUserAndVehicles(userId);
        return ResponseEntity.ok(result);

    }

    private ResponseEntity<List<Car>> fallbackListCars(@PathVariable("userId") int userId, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " tiene los coches en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallbackSaveCar(@PathVariable("userId") int userId, @RequestBody Car car, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " no tiene dinero para coches", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallbackListBikes(@PathVariable("userId") int userId, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallBackSaveBike(@PathVariable("userId") int userId, @RequestBody Bike bike, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + "  no tiene dinero para motos", HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> fallbackSaveBike(@PathVariable("userId") int userId, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " tiene los vehículos en el taller", HttpStatus.OK);
    }

}
