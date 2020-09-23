package rm.tempserver.service;

import java.time.Instant;
import org.springframework.stereotype.Service;
import rm.tempserver.json.ISpindelData;
import rm.tempserver.repository.ISpindelReading;

@Service
public class ISpindelService {

    public void readingReceived(ISpindelData data) {
        ISpindelReading reading = this.createSGReading(data);
    }

    private ISpindelReading createSGReading(ISpindelData data) {
        return new ISpindelReading()
                .setTime(Instant.now())
                .setName(data.getName())
                .setAngle(data.getAngle())
                .setTemperature(data.getTemperature())
                .setBattery(data.getBattery())
                .setGravity(data.getGravity());
    }
}
