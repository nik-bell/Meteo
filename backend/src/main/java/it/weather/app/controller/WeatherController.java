

package it.weather.app.controller;

import it.weather.app.model.Weather;
import it.weather.app.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*")
public class WeatherController {


      @Autowired
    private WeatherService weatherService;
    
    @PostMapping("/fetch")
    public ResponseEntity<Weather> fetchWeatherData(
            @RequestParam String city,
            @RequestParam double latitude,
            @RequestParam double longitude) {
        
        try {
            Weather weatherData = weatherService.fetchAndSaveWeatherData(city, latitude, longitude);
            return ResponseEntity.ok(weatherData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Weather>> getAllWeatherData() {
        List<Weather> data = weatherService.getAllWeatherData();
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/city/{city}")
    public ResponseEntity<List<Weather>> getWeatherDataByCity(@PathVariable String city) {
        List<Weather> data = weatherService.getWeatherDataByCity(city);
        return ResponseEntity.ok(data);
    }
    
}
