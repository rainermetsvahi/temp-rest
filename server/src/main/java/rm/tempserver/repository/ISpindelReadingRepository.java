package rm.tempserver.repository;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ISpindelReadingRepository {
    @Autowired
    private InfluxDB database;

    @Autowired
    private InfluxDBResultMapper resultMapper;

    public void add(ISpindelReading reading) {
        Point point = Point.measurement(ISpindelReading.MEASUREMENT)
            .time(System.currentTimeMillis(), ISpindelReading.TIME_UNIT)
            .tag("name", reading.getName())
            .addField("angle", reading.getAngle())
            .addField("temperature", reading.getTemperature())
            .addField("battery", reading.getBattery())
            .addField("gravity", reading.getGravity())
            .addField("sg", reading.getSg())
            .build();
        this.database.write(point);
    }
}
