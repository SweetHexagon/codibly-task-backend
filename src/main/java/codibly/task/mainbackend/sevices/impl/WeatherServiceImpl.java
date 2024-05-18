package codibly.task.mainbackend.sevices.impl;

import codibly.task.mainbackend.dtos.WeatherResponse;
import codibly.task.mainbackend.sevices.interfaces.WeatherService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
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
                .map(this::addEnergyGenerated)
                .onErrorMap(WebClientResponseException.class, ex -> {
                    HttpStatus status = (HttpStatus) ex.getStatusCode();
                    switch (status) {
                        case NOT_FOUND:
                            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Weather data not found for the provided location.");
                        case BAD_REQUEST:
                            return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request parameters.");
                        case INTERNAL_SERVER_ERROR:
                            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred on the server.");
                        default:
                            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + ex.getMessage());
                    }
                });
    }
    private WeatherResponse addEnergyGenerated(WeatherResponse response) {
        List<Double> daylightDurations = response.getDaily().getSunshineDuration();
        List<Double> energyGenerated = daylightDurations.stream()
                .map(duration -> 2.5 * (duration / 3600) * 0.2)
                .collect(Collectors.toList());
        response.setEnergyGenerated(energyGenerated);
        return response;
    }

}
