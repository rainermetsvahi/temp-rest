package rm.tempserver.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BoundParameterQuery;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rm.tempserver.json.ISpindelConfig;

@Component
public class ISpindelSetupRepository {
    @Autowired
    private InfluxDB database;

    @Autowired
    private InfluxDBResultMapper resultMapper;

    /**
     * Returns the latest configuration for the given iSpindeĺ
     */
    public Optional<ISpindelSetup> findByName(String name) {
        Query query = BoundParameterQuery.QueryBuilder.newQuery(
            "SELECT * FROM " + ISpindelSetup.MEASUREMENT
            + " WHERE ispindel_name = $name"
            + " ORDER BY time desc"
        )
            .bind("name", name)
            .create();

        List<ISpindelSetup> records = this.resultMapper
            .toPOJO(this.database.query(query), ISpindelSetup.class);

        return records.stream().findFirst();
    }

    public void add(ISpindelConfig ispindel) {
        Point point = Point.measurement(ISpindelSetup.MEASUREMENT)
            .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .tag("ispindel_name", ispindel.getName())
            .addField("formula", ispindel.getFormula())
            .build();
        this.database.write(point);
    }
}
