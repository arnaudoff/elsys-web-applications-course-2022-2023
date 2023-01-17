package de.mobile.cars;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CarsRepository {
    private List<Car> cars;

    public CarsRepository()
    {
        this.cars = new ArrayList<>();
    }

    public void insert(Car car) {
        this.cars.add(car);
    }

    public List<Car> findAll() {
        return this.cars;
    }

    public Optional<Car> findById(long id) {
        return this.cars.stream()
                .filter(car -> car.getId() == id)
                .findFirst();
    }
}
