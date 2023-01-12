package com.userservice.feignclients;

import com.userservice.Model.Bike;
import com.userservice.Model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bike-service")
@RequestMapping("/bike")
public interface BikeFeignClient {
    @PostMapping()
    Bike save (@RequestBody Bike bike);

    @GetMapping("/user/{userId}")
    List<Bike> getBikes(@PathVariable("userId") int userId);
}
