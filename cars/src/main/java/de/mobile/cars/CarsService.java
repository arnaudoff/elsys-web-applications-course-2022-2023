package de.mobile.cars;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarsService
{
    private CarsRepository carsRepository;

    public CarsService(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }

    public void addCar(AddCarRequest carRequest)
    {
        Car car = new Car();
        car.setId(10);
        car.setMake(carRequest.getMake());
        car.setModel(carRequest.getModel());
        car.setYear(carRequest.getYear());

        this.carsRepository.insert(car);
    }

    public Optional<Car> getCar(long id) {
        return this.carsRepository.findById(id);
    }

    public List<Car> getAll() {
        return this.carsRepository.findAll();
    }
}