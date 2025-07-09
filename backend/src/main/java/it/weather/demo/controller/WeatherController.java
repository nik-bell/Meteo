

package it.weather.controller;

import it.weather.model.WeatherData;
import it.weather.service.WeatherService;
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
    public ResponseEntity<WeatherData> fetchWeatherData(
            @RequestParam String city,
            @RequestParam double latitude,
            @RequestParam double longitude) {
        
        try {
            WeatherData weatherData = weatherService.fetchAndSaveWeatherData(city, latitude, longitude);
            return ResponseEntity.ok(weatherData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<WeatherData>> getAllWeatherData() {
        List<WeatherData> data = weatherService.getAllWeatherData();
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/city/{city}")
    public ResponseEntity<List<WeatherData>> getWeatherDataByCity(@PathVariable String city) {
        List<WeatherData> data = weatherService.getWeatherDataByCity(city);
        return ResponseEntity.ok(data);
    }
    
}
