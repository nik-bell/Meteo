
package it.weather.app.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.weather.app.model.Weather;
import it.weather.app.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository repository;
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public Weather fetchAndSaveWeatherData(String city, double latitude, double longitude) {
        try {
            // Chiama l'API OpenMeteo
            String url = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%.2f&longitude=%.2f&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m",
                latitude, longitude
            );
            
            String response = restTemplate.getForObject(url, String.class);
            JsonNode jsonNode = objectMapper.readTree(response);
            
            // Estrai i dati dal JSON
            JsonNode currentWeather = jsonNode.get("current_weather");
            double temperature = currentWeather.get("temperature").asDouble();
            double windSpeed = currentWeather.get("windspeed").asDouble();
            
            // Prendi l'umidità dall'array hourly (primo valore)
            JsonNode hourly = jsonNode.get("hourly");
            double humidity = hourly.get("relativehumidity_2m").get(0).asDouble();
            
            // Salva nel database
            Weather weatherData = new Weather(city, temperature, humidity, windSpeed);
            return repository.save(weatherData);
            
        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero dei dati meteo: " + e.getMessage());
        }
    }
    
    public List<Weather> getAllWeatherData() {
        return repository.findAllOrderByTimestampDesc();
    }
    
    public List<Weather> getWeatherDataByCity(String city) {
        return repository.findByCityOrderByTimestampDesc(city);
    }

}
