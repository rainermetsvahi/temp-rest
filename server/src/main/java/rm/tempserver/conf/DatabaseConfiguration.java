package rm.tempserver.conf;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Data source configuration
 */
@Configuration
public class DatabaseConfiguration {

    @Value("${data-source.url}")
    private String serverUrl;

    @Value("${data-source.database}")
    private String database;

    @Value("${data-source.username}")
    private String username;

    @Value("${data-source.password}")
    private String password;

    @Bean
    InfluxDB connection() {
        return InfluxDBFactory.connect(this.serverUrl, this.username, this.password)
            .setDatabase(this.database);
    }

    @Bean
    InfluxDBResultMapper resultMapper() {
        return new InfluxDBResultMapper();
    }
}
