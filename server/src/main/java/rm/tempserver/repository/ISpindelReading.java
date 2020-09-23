package rm.tempserver.repository;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.influxdb.annotation.TimeColumn;

@Data
@Accessors(chain = true)
@Measurement(name = ISpindelReading.MEASUREMENT)
@AllArgsConstructor
@NoArgsConstructor
public class ISpindelReading {
    static final String MEASUREMENT = "ispindel_reading";

    @TimeColumn(timeUnit = TimeUnit.SECONDS)
    @Column(name = "time")
    private Instant time;

    @Column(name = "name")
    private String name;

    @Column(name = "angle")
    private Double angle;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "battery")
    private Double battery;

    @Column(name = "gravity")
    private Double gravity;

    // special gravity
    @Column(name = "sg")
    private Double sg;
}
