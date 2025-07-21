
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
            // Chiama l'API OpenMeteo con i parametri corretti
            String url = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%.4f&longitude=%.4f&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m&forecast_days=1",
                latitude, longitude
            );

            System.out.println("Calling OpenMeteo API: " + url);

            String response = restTemplate.getForObject(url, String.class);
            System.out.println("OpenMeteo Response: " + response);

            JsonNode jsonNode = objectMapper.readTree(response);

            // Estrai i dati dal JSON usando la struttura API corretta
            JsonNode hourly = jsonNode.get("hourly");
            if (hourly == null) {
                throw new RuntimeException("No hourly weather data found in API response");
            }

            // Prendi i dati più recenti (ora corrente)
            JsonNode temperatureArray = hourly.get("temperature_2m");
            JsonNode humidityArray = hourly.get("relative_humidity_2m");
            JsonNode windSpeedArray = hourly.get("wind_speed_10m");

            if (temperatureArray == null || humidityArray == null || windSpeedArray == null) {
                throw new RuntimeException("Missing weather data in API response");
            }

            // Trova l'indice dell'ora corrente o la più vicina
            int currentIndex = Math.min(temperatureArray.size() - 1, java.time.LocalDateTime.now().getHour());
            
            double temperature = temperatureArray.get(currentIndex).asDouble();
            double humidity = humidityArray.get(currentIndex).asDouble();
            double windSpeed = windSpeedArray.get(currentIndex).asDouble();

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
