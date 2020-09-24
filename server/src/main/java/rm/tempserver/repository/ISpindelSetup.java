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
@Measurement(name = ISpindelSetup.MEASUREMENT)
@AllArgsConstructor
@NoArgsConstructor
public class ISpindelSetup {
    static final String MEASUREMENT = "ispindel_config";

    @TimeColumn(timeUnit = TimeUnit.SECONDS)
    @Column(name = "time")
    private Instant time;

    @Column(name = "ispindel_name")
    private String name;

    @Column(name = "formula")
    private String formula;
}
