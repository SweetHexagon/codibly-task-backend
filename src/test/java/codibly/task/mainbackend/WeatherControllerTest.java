package codibly.task.mainbackend;

import codibly.task.mainbackend.controllers.WeatherController;
import codibly.task.mainbackend.dtos.WeatherResponse;
import codibly.task.mainbackend.sevices.interfaces.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

@WebFluxTest(WeatherController.class)
public class WeatherControllerTest {

    @MockBean
    private WeatherService weatherService;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeatherValidRequest() {
        when(weatherService.fetchWeatherData(anyDouble(), anyDouble())).thenReturn(Mono.just(new WeatherResponse()));

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/weather")
                                .queryParam("latitude", "40.7128")
                                .queryParam("longitude", "-74.0060")
                                .build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testGetWeatherInvalidLatitude() {
        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/weather")
                                .queryParam("latitude", "100.0000")
                                .queryParam("longitude", "-74.0060")
                                .build())
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    public void testGetWeatherInvalidLongitude() {
        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/weather")
                                .queryParam("latitude", "40.7128")
                                .queryParam("longitude", "200.0000")
                                .build())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testGetWeatherMissingLatitude() {
        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/weather")
                                .queryParam("longitude", "-74.0060")
                                .build())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testGetWeatherMissingLongitude() {
        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/weather")
                                .queryParam("latitude", "40.7128")
                                .build())
                .exchange()
                .expectStatus().isBadRequest();
    }
}