package rm.tempserver.repository;

import org.influxdb.InfluxDB;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ISpindelReadingRepository {
    @Autowired
    private InfluxDB database;

    @Autowired
    private InfluxDBResultMapper resultMapper;
}
