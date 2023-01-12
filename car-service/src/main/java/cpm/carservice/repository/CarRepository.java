package cpm.carservice.repository;

import cpm.carservice.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository  extends JpaRepository<Car, Integer> {

    List<Car> findByUserId(int userId);

}
