
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

            System.out.println("Calling OpenMeteo API: " + url);

            String response = restTemplate.getForObject(url, String.class);
            System.out.println("OpenMeteo Response: " + response);

            JsonNode jsonNode = objectMapper.readTree(response);

            // Estrai i dati dal JSON usando la nuova struttura API
            JsonNode current = jsonNode.get("current");
            if (current == null) {
                throw new RuntimeException("No current weather data found in API response");
            }

            double temperature = current.get("temperature_2m").asDouble();
            double humidity = current.get("relative_humidity_2m").asDouble();
            double windSpeed = current.get("wind_speed_10m").asDouble();

            System.out.println("Parsed weather data - Temperature: " + temperature +
                    ", Humidity: " + humidity + ", Wind Speed: " + windSpeed);

            // Salva nel database
            Weather weatherData = new Weather(city, temperature, humidity, windSpeed);
            Weather saved = repository.save(weatherData);
            System.out.println("Weather data saved with ID: " + saved.getId());

            return saved;

        } catch (Exception e) {
            System.err.println("Error in fetchAndSaveWeatherData: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Errore nel recupero dei dati meteo: " + e.getMessage());
        }
    }

    public List<Weather> getAllWeatherData() {
        try {
            return repository.findAllOrderByTimestampDesc();
        } catch (Exception e) {
            System.err.println("Error getting all weather data: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Errore nel recupero dei dati dal database: " + e.getMessage());
        }
    }

    public List<Weather> getWeatherDataByCity(String city) {
        try {
            return repository.findByCityOrderByTimestampDesc(city);
        } catch (Exception e) {
            System.err.println("Error getting weather data by city: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Errore nel recupero dei dati per la città: " + e.getMessage());
        }
    }
}
