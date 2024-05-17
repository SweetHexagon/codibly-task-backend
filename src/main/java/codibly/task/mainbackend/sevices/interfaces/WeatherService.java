package codibly.task.mainbackend.sevices.interfaces;
import codibly.task.mainbackend.dtos.WeatherResponse;
import reactor.core.publisher.Mono;

public interface WeatherService {
    Mono<WeatherResponse> fetchWeatherData(double latitude, double longitude);
}
