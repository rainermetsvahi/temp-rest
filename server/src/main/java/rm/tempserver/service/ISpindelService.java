package rm.tempserver.service;

import lombok.extern.slf4j.Slf4j;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rm.tempserver.json.ISpindelConfig;
import rm.tempserver.json.ISpindelData;
import rm.tempserver.repository.ISpindelReading;
import rm.tempserver.repository.ISpindelReadingRepository;
import rm.tempserver.repository.ISpindelSetupRepository;

@Service
@Slf4j
public class ISpindelService {
    private static final String TILT_VAR = "tilt";
    private static final double VALIDATION_TILT_VALUE = 25.0;
    // SG value range for validating the formula with the test tilt value above
    // in reality, 25 degrees ISpindel tilt means SG = 1.000
    private static final double VALIDATION_MIN_SG = 0.95;
    private static final double VALIDATION_MAX_SG = 1.05;

    @Autowired
    private ISpindelSetupRepository setupRepository;

    @Autowired
    private ISpindelReadingRepository readingRepository;

    public void readingReceived(ISpindelData data) {
        this.readingRepository.add(this.create(data));
    }

    public void updateConfig(ISpindelConfig config) {
        validate(config);
        this.setupRepository.add(config);
    }

    private ISpindelReading create(ISpindelData data) {
        ISpindelReading reading = new ISpindelReading()
            .setName(data.getName())
            .setAngle(data.getAngle())
            .setTemperature(data.getTemperature())
            .setBattery(data.getBattery())
            .setGravity(data.getGravity());

        // set the SG if setup for the iSpindel exists
        this.setupRepository.findByName(data.getName())
            .ifPresent(s -> reading.setSg(calculateSG(data.getAngle(), s.getFormula())));

        return reading;
    }

    private static Double calculateSG(double angle, String formula) {
        double sg = new ExpressionBuilder(formula)
            .variables(TILT_VAR)
            .build()
            .setVariable(TILT_VAR, angle)
            .evaluate();
        return Math.round(sg * 1000d) / 1000d; // round to 3 places
    }

    private static void validate(ISpindelConfig config) {
        if (config.getName() == null || config.getName().isEmpty()) {
            throw new IllegalArgumentException("ISpindel name cannot be empty");
        }
        if (config.getFormula() == null || config.getFormula().isEmpty()) {
            throw new IllegalArgumentException("ISpindel SF formula cannot be empty");
        }
        if (!config.getFormula().contains(TILT_VAR)) {
            throw new IllegalArgumentException("SG formula must include '" + TILT_VAR + "'");
        }
        // perform a calculation to test whether we got a valid formula
        double sg = calculateSG(VALIDATION_TILT_VALUE, config.getFormula());
        if (sg < VALIDATION_MIN_SG || sg > VALIDATION_MAX_SG) {
            throw new IllegalArgumentException(
                String.format(
                    "The formula returned: %s that is not in expected range (%s, %s)",
                    sg,
                    VALIDATION_MIN_SG,
                    VALIDATION_MAX_SG
                )
            );
        }
    }
}
