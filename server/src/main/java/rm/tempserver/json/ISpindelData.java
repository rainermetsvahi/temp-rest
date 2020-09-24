package rm.tempserver.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data sent by ISpindel
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ISpindelData {
    private String name;
    private Double angle;
    private Double temperature;

    @JsonProperty("temp_units")
    private String temperatureUnit;

    private Double battery;
    private Double gravity;

    @JsonProperty("interval")
    private Integer intervalSeconds;
}
