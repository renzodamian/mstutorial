package cpm.carservice.controller;

import cpm.carservice.entities.Car;
import cpm.carservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity <List<Car>> listCar(){
        List<Car> cars = carService.getAllCars();
        if (cars.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }
    @GetMapping("/{id}")
    public ResponseEntity <Car> getCar(@PathVariable("id") int id){
        Car car = carService.getCarById(id);
        if (car == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(car);
    }
    @PostMapping
    public ResponseEntity <Car> addCar(@RequestBody Car car){
        Car newCar = carService.saveCar(car);
        return ResponseEntity.ok(newCar);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity <List<Car>> getCarByUserId(@PathVariable("userId") int userId){
        List<Car> cars = carService.byUserId(userId);
        return ResponseEntity.ok(cars);
    }
}

