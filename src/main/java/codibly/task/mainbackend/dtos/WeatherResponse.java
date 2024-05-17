package codibly.task.mainbackend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    @Getter @Setter
    public double latitude;
    @Getter @Setter
    public double longitude;
    @Getter @Setter
    public String timezone;
    @Getter @Setter
    public Daily daily;
    @Getter @Setter
    @JsonProperty("energyGenerated")
    public List<Double> energyGenerated;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Daily {
        private List<String> time;

        @JsonProperty("weather_code")
        private List<Integer> weatherCode;

        @JsonProperty("temperature_2m_max")
        private List<Double> temperature2mMax;

        @JsonProperty("temperature_2m_min")
        private List<Double> temperature2mMin;

        @JsonProperty("daylight_duration")
        private List<Double> daylightDuration;

        @JsonProperty("sunshine_duration")
        private List<Double> sunshineDuration;
    }
}