package com.userservice.feignclients;

import com.userservice.Model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "car-service", url ="http://localhost:8002")
@RequestMapping("/car")
public interface CarFeignClient {
    @PostMapping()
    Car save (@RequestBody Car car);

    @GetMapping("/user/{userId}")
    List<Car> getCars(@PathVariable("userId") int userId);
}
