package codibly.task.mainbackend.sevices.impl;

import codibly.task.mainbackend.dtos.WeatherResponse;
import codibly.task.mainbackend.sevices.interfaces.WeatherService;

import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final WebClient webClient;

    public WeatherServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.open-meteo.com/v1").build();
    }

    @Override
    public Mono<WeatherResponse> fetchWeatherData(double latitude, double longitude) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/forecast")
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam("daily", "weather_code,temperature_2m_max,temperature_2m_min,daylight_duration,sunshine_duration")
                        .build())
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .map(this::addEnergyGenerated);
    }
    private WeatherResponse addEnergyGenerated(WeatherResponse response) {
        List<Double> daylightDurations = response.getDaily().getDaylightDuration();
        List<Double> energyGenerated = daylightDurations.stream()
                .map(duration -> 2.5 * (duration / 3600) * 0.2)
                .collect(Collectors.toList());
        response.setEnergyGenerated(energyGenerated);
        return response;
    }

}
