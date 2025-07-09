
package it.weather.repository;

import it.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface WeatherRepository {

    List<Weather> findByCityOrderByTimestampDesc(String city);
    
    @Query("SELECT w FROM WeatherData w WHERE w.city = ?1 ORDER BY w.timestamp DESC")
    List<Weather> findLatestByCityLimit(String city);
    
    @Query("SELECT w FROM WeatherData w ORDER BY w.timestamp DESC")
    List<Weather> findAllOrderByTimestampDesc();

}
