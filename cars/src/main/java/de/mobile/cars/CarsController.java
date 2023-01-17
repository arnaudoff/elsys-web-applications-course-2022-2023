package de.mobile.cars;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("cars")
public class CarsController {
    private CarsService carsService;

    public CarsController(CarsService carsService) {
        this.carsService = carsService;
    }

    // POST localhost:8080/cars {
    //    "make": "Skoda",
    //    "model": "Rapid",
    //    "year": 2015
    //}
    @PostMapping
    public void addCar(@RequestBody AddCarRequest addCarRequest) {
        this.carsService.addCar(addCarRequest);
    }

    // {"name": "niki", "age": 15}
    // [] -> ["pesho", "gechovski", "naidenov"], [13, 15, 15]
    // [{}] -> [{"name": "niki", "age": 15}, {"make": "skoda", "model": "octavia"}]
    // GET localhost:3000/cars
    // [{}, ..., {}]
    @GetMapping
    public List<Car> getAll()
    {
        return this.carsService.getAll();
    }

    // GET localhost:3000/cars/1337
    // {}
    @GetMapping("/{carId}")
    public Optional<Car> getCar(@PathVariable long carId) {
        return this.carsService.getCar(carId);
    }
}