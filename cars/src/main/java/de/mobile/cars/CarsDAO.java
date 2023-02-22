package de.mobile.cars;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class CarsDAO
{
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CarsDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate)
    {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void insert(Car car)
    {
        String sql = "INSERT INTO cars(make, model, year_built) VALUES (:make, :model, :year_built)";

        Map<String, Object> params = new HashMap<>();
        params.put("make", car.getMake());
        params.put("model", car.getModel());
        params.put("year_built", car.getYear());

        this.namedParameterJdbcTemplate.update(sql, params);
    }

    public List<Car> findAll()
    {
        String sql = "SELECT make, model, year_built FROM cars";

        return this.namedParameterJdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Car.class));
    }

    public Optional<Car> findById(long id)
    {
        String sql = "SELECT * FROM cars WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        try
        {
            Car car = this.namedParameterJdbcTemplate.queryForObject(sql,
                    params,
                    BeanPropertyRowMapper.newInstance(Car.class));

            return Optional.of(car);
        }
        catch (EmptyResultDataAccessException e)
        {
            return Optional.empty();
        }
    }

}
