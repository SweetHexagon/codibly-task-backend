package codibly.task.mainbackend.controllers;

import codibly.task.mainbackend.dtos.WeatherResponse;
import codibly.task.mainbackend.sevices.interfaces.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public Mono<WeatherResponse> getWeather(@RequestParam double latitude, @RequestParam double longitude) {
        return weatherService.fetchWeatherData(latitude, longitude);
    }
}

