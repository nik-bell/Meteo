
package it.weather.app.repository;

import it.weather.app.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface WeatherRepository extends JpaRepository<Weather, String> {

    List<Weather> findByCityOrderByTimestampDesc(String city);
    
    @Query("SELECT w FROM Weather w WHERE w.city = ?1 ORDER BY w.timestamp DESC")
    List<Weather> findLatestByCityLimit(String city);
    
    @Query("SELECT w FROM Weather w ORDER BY w.timestamp DESC")
    List<Weather> findAllOrderByTimestampDesc();

}
