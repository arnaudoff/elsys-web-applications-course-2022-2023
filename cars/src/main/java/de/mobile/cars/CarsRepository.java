package de.mobile.cars;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CarsRepository
{
    private CarsDAO carsDAO;

    public CarsRepository(CarsDAO carsDAO)
    {
        this.carsDAO = carsDAO;
    }

    public void insert(Car car)
    {
        this.carsDAO.insert(car);
    }

    public List<Car> findAll()
    {
        return this.carsDAO.findAll();
    }

    public Optional<Car> findById(long id)
    {
        return this.carsDAO.findById(id);
    }
}
